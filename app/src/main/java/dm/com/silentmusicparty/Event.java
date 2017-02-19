package dm.com.silentmusicparty;

/**
 * Created by SESA72162 on 2/18/2017.
 */

public class Event
{
    private int id;
    private String name;
    private String organizer;
    private String place;
    private String description;
    private String date;
    private String time;
    private String duration;
    private String invitees;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getOrganizer()
    {
        return organizer;
    }

    public void setOrganizer(String organizer)
    {
        this.organizer = organizer;
    }

    public String getPlace()
    {
        return place;
    }

    public void setPlace(String place)
    {
        this.place = place;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }

    public String getInvitees()
    {
        return invitees;
    }

    public void setInvitees(String invitees)
    {
        this.invitees = invitees;
    }
}
