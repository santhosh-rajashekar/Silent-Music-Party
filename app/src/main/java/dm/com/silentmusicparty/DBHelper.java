package dm.com.silentmusicparty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "SilentMusicParty.db";
    public static final String CONTACTS_TABLE_NAME = "Members";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "city";
    public static final String CONTACTS_COLUMN_PINCODE = "pincode";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    private HashMap hp;

    public static final String CONTACTS_TABLE_EVENTS = "Events";
    public static final String EVENT_ID = "id";
    public static final String EVENT_NAME = "name";
    public static final String EVENT_VENUE = "place";
    public static final String EVENT_DATE = "date";
    public static final String EVENT_TIME = "time";
    public static final String EVENT_DESCRIPTION = "description";
    public static final String EVENT_DURATION = "duration";
    public static final String EVENT_PHOTO = "photo";
    public static final String EVENT_ORGANIZER = "organizer";
    public static final String EVENT_ORGANIZER_ID = "organizer_id";
    public static final String EVENT_PARTICIPANTS_IDS = "participant_ids";
    public static final String EVENT_PARTICIPANTS = "participants";

    private Context context = null;
    private static DBHelper dbHelper;

    private DBHelper(Context context)
    {
        super(context.getApplicationContext(), DATABASE_NAME, null, 1);
        this.context = context.getApplicationContext();
    }

    public static synchronized DBHelper getInstance(Context context)
    {
        if (dbHelper == null)
        {
            dbHelper = new DBHelper(context.getApplicationContext());
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(
                "create table Members " +
                        "(id integer primary key, name text,email text,password text, street text, city text, pincode text, phone text, photo blob)"
        );

        db.execSQL(
                "create table Events " +
                        "(id integer primary key, name text,place text,description text, date text,time text, duration text, organizer text, participants text)"
        );

        addEvents(db);
        addMembers(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS Members");
        db.execSQL("DROP TABLE IF EXISTS Events");
        onCreate(db);
    }

    private void addMembers(SQLiteDatabase db)
    {
        if (this.context != null)
        {
            Member member = new Member();
            member.setName("Santhosha Rajashekar");
            member.setEmail("santhoshniecampus@gmail.com");
            member.setPassword("password123");
            member.setPhone("+4916096418752");
            member.setAddress("Wuerzburg, Ludwigstrasse 21, 97070");
            Bitmap photo = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.santhosha);
            member.setProfile_photo(photo);
            addMember(db, member);

            member = new Member();
            member.setName("Anitha S");
            member.setEmail("anithasj1789@gmail.com");
            member.setPassword("password123");
            member.setPhone("+49 176 59929231");
            member.setAddress("Wuerzburg, Ludwigstrasse 21, 97070");
            photo = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.anitha);
            member.setProfile_photo(photo);
            //this.insertMember(member.getName(), member.getPhone(), member.getEmail(), "Wurzburg", "Ludwigstrasse 21", "97070", photo);
            addMember(db, member);
        }
    }

    private void addEvents(SQLiteDatabase db)
    {
        Event event = new Event();
        event.setName("Donaueschingen Musics Festival");
        event.setOrganizer("Santhosha");
        event.setPlace("Berlin");
        event.setDate("02-03-2017");
        event.setTime("18:00");
        event.setDuration("120 Minutes");
        event.setDescription("A vital party of the history of electronic music, this festival paved the way to the concept of establishing small festivals to present new and experimental musicians. After World War I, a significant increase in new electronic instruments took place, becoming featured elements of the festival. In 1926, Jorg Mager invented an electronic instrument without a keyboard, called the Spharophon, among others, which was shown at the festival.");
        String participants = "Britni Horning,Bill Wenrich,Scott Marts,Francene Levasseur,Cythia Sepeda,Omar Connelly,Kareen Cockrill";
        event.setInvitees(participants);

        List<String> invitees = new ArrayList<String>();
        invitees.add("Britni Horning");
        invitees.add("Bill Wenrich");
        invitees.add("Scott Marts");
        invitees.add("Francene Levasseur");
        invitees.add("Cythia Sepeda");

        addEvent(db, event);

        event = new Event();
        event.setName("The Chicago White Party");
        event.setOrganizer("Rajashekar");
        event.setPlace("Bremen");
        event.setDate("09-03-2017");
        event.setTime("18:00");
        event.setDuration("180 Minutes");
        event.setDescription("One of the first White Party parties was held August 8, 1974, hosted by Chicago business owner Chuck Renslow to celebrate his birthday and thank his patrons. It was then held for the next 36 years until 2010. The largest party was held in 1986 at Navy pier with 5000 participants");
        participants = "Meghan Anson,Andrew Morris,Paz Gullickson,Enola Hofman,Donnell Easterling";
        event.setInvitees(participants);

        addEvent(db, event);
    }

    private void addMember(SQLiteDatabase db, Member member)
    {
        String address = member.getAddress();
        String city = "";
        String street = "";
        String pincode = "";

        if (address != null)
        {
            String[] details = address.split(",");

            if (details != null && details.length > 2)
            {
                city = details[0];
                street = details[1];
                pincode = details[2];
            }
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", member.getName());
        contentValues.put("phone", member.getPhone());
        contentValues.put("email", member.getEmail());
        contentValues.put("password", member.getPassword());
        contentValues.put("city", city);
        contentValues.put("street", street);
        contentValues.put("pincode", pincode);

        byte[] photo_blob = getBitmapAsByteArray(member.getProfile_photo());
        contentValues.put("photo", photo_blob);

        db.insert("Members", null, contentValues);
    }

    private void addEvent(SQLiteDatabase db, Event event)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", event.getName());
        contentValues.put("description", event.getDescription());
        contentValues.put("organizer", event.getOrganizer());
        contentValues.put("place", event.getPlace());
        contentValues.put("date", event.getDate());
        contentValues.put("time", event.getTime());
        contentValues.put("duration", event.getDuration());
        //contentValues.put("photo", "");
        contentValues.put("participants", event.getInvitees());

        db.insert("Events", null, contentValues);
    }

    public boolean isValidLogin(String email, String password)
    {
        boolean isValidLogin = false;
        List<Member> memberList = getAllMembers(this.context);

        for (int i = 0; i < memberList.size(); i++)
        {
            String emailInDb = memberList.get(i).getEmail();
            String passwordInDb = memberList.get(i).getPassword();

            if (emailInDb != null && emailInDb.equalsIgnoreCase(email))
            {
                if (passwordInDb != null && passwordInDb.contentEquals(password))
                {
                    isValidLogin = true;
                    break;
                }
            }
        }

        return isValidLogin;
    }

    public boolean insertMember(String name, String phone, String email, String city, String street, String pincode, Bitmap photo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("city", city);
        contentValues.put("street", street);
        contentValues.put("pincode", pincode);

        byte[] photo_blob = getBitmapAsByteArray(photo);
        contentValues.put("photo", photo_blob);

        db.insert("Members", null, contentValues);
        return true;
    }

    public boolean insertEvents(String name, String organizer, String place, String date, String time, String duration, String description, String photo, List<String> participants)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", name);
        contentValues.put("organizer", organizer);
        contentValues.put("place", place);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("duration", duration);
        //contentValues.put("photo", "");

        String invitees = "";

        for (int i = 0; i < participants.size(); i++)
        {
            invitees += participants.get(i) + ",";
        }

        char separator = ',';
        invitees.trim();
        invitees = trimEnd(invitees);

        contentValues.put("participants", invitees);

        db.insert("Events", null, contentValues);
        return true;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public boolean insertEvents(String name, String organizer, String place, String date, String time, String duration, String description, String photo, String participants)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("organizer", organizer);
        contentValues.put("place", place);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("duration", duration);
        //contentValues.put("photo", "");
        contentValues.put("participants", participants);

        db.insert("Events", null, contentValues);
        return true;
    }

    public String trimEnd(String str)
    {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == 'x')
        {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public Cursor getData(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from contacts where id=" + id + "", null);
        return res;
    }

    public int numberOfRows()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact(Integer id, String name, String phone, String email, String street, String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteContact(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from contacts", null);
        res.moveToFirst();

        while (res.isAfterLast() == false)
        {
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

    public List<Event> getAllEvents(Context context)
    {
        List<Event> events = new ArrayList<Event>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from Events", null);
        Event event = null;

        if (result.moveToFirst())
        {
            do
            {
                int id = result.getInt(result.getColumnIndex("id"));
                String name = result.getString(result.getColumnIndex("name"));
                String organizer = result.getString(result.getColumnIndex("organizer"));
                String place = result.getString(result.getColumnIndex("place"));
                String date = result.getString(result.getColumnIndex("date"));
                String time = result.getString(result.getColumnIndex("time"));
                String duration = result.getString(result.getColumnIndex("duration"));
                String description = result.getString(result.getColumnIndex("description"));

                event = new Event();
                event.setId(id);
                event.setName(name);
                event.setPlace(place);
                event.setOrganizer(organizer);
                event.setDescription(description);
                event.setTime(time);
                event.setDuration(duration);
                event.setDate(date);

                events.add(event);
            }
            while (result.moveToNext());

            result.close();
        }
        else
        {
            result.close();
        }

        return events;
    }

    public List<Member> getAllMembers(Context context)
    {
        List<Member> members = new ArrayList<Member>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from Members", null);
        result.moveToFirst();

        while (result.isAfterLast() == false)
        {
            int id = result.getInt(result.getColumnIndex("id"));
            String name = result.getString(result.getColumnIndex("name"));
            String email = result.getString(result.getColumnIndex("email"));
            String password = result.getString(result.getColumnIndex("password"));
            String street = result.getString(result.getColumnIndex("street"));
            String city = result.getString(result.getColumnIndex("city"));
            String pinocde = result.getString(result.getColumnIndex("pincode"));
            String phone = result.getString(result.getColumnIndex("phone"));
            byte[] photo_blob = result.getBlob(result.getColumnIndex("photo"));
            Bitmap photo = BitmapFactory.decodeByteArray(photo_blob, 0, photo_blob.length);

            Member member = new Member();
            member.setId(id);
            member.setName(name);
            member.setEmail(email);
            member.setPassword(password);
            member.setPhone(phone);
            String address = city + "," + street + "," + pinocde;
            member.setAddress(address);
            member.setProfile_photo(photo);

            members.add(member);

            result.moveToNext();
        }

        return members;
    }
}
