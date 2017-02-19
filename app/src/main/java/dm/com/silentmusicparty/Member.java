package dm.com.silentmusicparty;

import android.graphics.Bitmap;

/**
 * Created by SESA72162 on 2/18/2017.
 */

public class Member
{
    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Bitmap profile_photo;

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

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Bitmap getProfile_photo()
    {
        return profile_photo;
    }

    public void setProfile_photo(Bitmap profile_photo)
    {
        this.profile_photo = profile_photo;
    }
}
