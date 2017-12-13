package ie.mid.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import ie.mid.model.AvailableCard;
import ie.mid.model.Profile;

public class DatabaseHandler {

    //Database variables
    private static final String DATA_BASE_NAME = "mid_database";
    private static final int DATABASE_VERSION = 1;
    private static final String ID = "id";
    private static final String CREATION_DATE = "creation_date";
    private static final String UPDATED_DATE = "updated_date";

    //Profile data variables
    private static final String PROFILE_NAME = "profile_name";
    private static final String PROFILE_IMG = "profile_img";
    private static final String SALT = "salt";
    private static final String HASH = "hash";
    private static final String SERVER_ID = "server_id";
    private static final String PRIVATE_KEY = "private_key";
    private static final String PUBLIC_KEY = "public_key";
    private static final String PROFILE_TABLE_NAME = "profiles";
    private static final String PROFILE_TABLE_CREATE = "create table " + PROFILE_TABLE_NAME + " (" +
            ID + " text not null, " +
            PROFILE_NAME + " text not null," +
            PROFILE_IMG + " text not null," +
            SALT + " text not null," +
            HASH + " text not null," +
            SERVER_ID + " text not null," +
            PRIVATE_KEY + " text not null," +
            PUBLIC_KEY + " text not null," +
            CREATION_DATE + " text not null," +
            UPDATED_DATE + " text not null" +
            ");";

    //Card data variables
    private static final String CARD_ID = "card_id";
    private static final String DATA = "data";
    private static final String OWNER = "owner";
    private static final String STATUS_CODE = "status_code";
    private static final String CARD_TABLE_NAME = "cards";
    private static final String CARD_TABLE_CREATE = "create table " + CARD_TABLE_NAME + " (" +
            ID + " text not null, " +
            CARD_ID + " text not null," +
            OWNER + " text not null," +
            DATA + " text not null," +
            STATUS_CODE + " text not null," +
            CREATION_DATE + " text not null," +
            UPDATED_DATE + " text not null" +
            ");";

    //Available card variables
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String CARD_FIELDS = "card_fields";
    private static final String ICON_IMG_URL = "icon_img_url";
    private static final String COVER_IMG_URL = "cover_img_url";
    private static final String BACKEND_ID = "backend_id";
    private static final String VERSION_NUMBER = "version_number";
    private static final String AVAILABLE_CARD_TABLE_NAME = "available_cards";
    private static final String AVAILABLE_CARD_TABLE_CREATE = "create table " + AVAILABLE_CARD_TABLE_NAME + " (" +
            ID + " text not null, " +
            TITLE + " text not null, " +
            DESCRIPTION + " text not null," +
            CARD_FIELDS + " text not null," +
            BACKEND_ID + " text not null, " +
            COVER_IMG_URL + " text not null," +
            ICON_IMG_URL + " text not null," +
            VERSION_NUMBER + " text not null" +
            ");";

    //FCM database
    private static final String FCM_TOKEN = "fcm_token";
    private static final String FCM_TABLE_NAME = "fcm_token";
    private static final String FCM_TABLE_NAME_CREATE = "create table " + FCM_TABLE_NAME + " (" +
            ID + " text not null, " +
            FCM_TOKEN + " text not null, " +
            CREATION_DATE + " text not null," +
            UPDATED_DATE + " text not null" +
            ");";


    private static final String[] TABLE_NAMES = new String []{PROFILE_TABLE_NAME,CARD_TABLE_NAME,AVAILABLE_CARD_TABLE_NAME,FCM_TABLE_NAME};
    private static final String[] CREATION_SCRIPTS = new String []{PROFILE_TABLE_CREATE,CARD_TABLE_CREATE,AVAILABLE_CARD_TABLE_CREATE,FCM_TABLE_NAME_CREATE};
    private DataBaseHelper dbhelper;
    private SQLiteDatabase db;


    public DatabaseHandler(Context ctx)
    {
        dbhelper = new DataBaseHelper(ctx);
    }

    private static class DataBaseHelper extends SQLiteOpenHelper
    {

        DataBaseHelper(Context ctx)
        {
            super(ctx,DATA_BASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try
            {
                for(String script: CREATION_SCRIPTS){
                    db.execSQL(script);
                }
            }

            catch(SQLException e)
            {
                Log.e("Error", e.getMessage());
            }
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            for(String name: TABLE_NAMES){
                db.execSQL("DROP TABLE IF EXISTS " + name);
            }
            onCreate(db);
        }
    }

    public DatabaseHandler open()
    {
        db = dbhelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        dbhelper.close();
    }

    public Profile createProfile(String name,String profileImg,String hash,String salt,String serverId, String publicKey,String privateKey){
        String id = UUID.randomUUID().toString();
        String date = new Date().toString();
        ContentValues content = new ContentValues();
        content.put(ID, id);
        content.put(PROFILE_NAME,name);
        content.put(PROFILE_IMG,profileImg);
        content.put(SERVER_ID,serverId);
        content.put(HASH,privateKey);
        content.put(SALT,publicKey);
        content.put(PRIVATE_KEY,privateKey);
        content.put(PUBLIC_KEY,publicKey);
        content.put(CREATION_DATE, date);
        content.put(UPDATED_DATE, date);
        db.insert(PROFILE_TABLE_NAME,null,content);
        return new Profile(id,name,profileImg,hash,salt,serverId,publicKey,privateKey);
    }

    public AvailableCard createAvailableCard(String title, String iconUrl, String coverUrl,String versionNumber){
        String id = UUID.randomUUID().toString();
        ContentValues content = new ContentValues();
        content.put(ID, id);
        content.put(TITLE,title);
        content.put(COVER_IMG_URL,coverUrl);
        content.put(ICON_IMG_URL,iconUrl);
        content.put(VERSION_NUMBER, versionNumber);
        db.insert(AVAILABLE_CARD_TABLE_NAME,null,content);
        return new AvailableCard(id,title,iconUrl,coverUrl,versionNumber);
    }

    public String createCard(String title, String description,String owner,String data,String statusCode)
    {
        String id = UUID.randomUUID().toString();
        String date = new Date().toString();
        ContentValues content = new ContentValues();
        content.put(ID, id);
        content.put(TITLE,title);
        content.put(description,owner);
        content.put(OWNER,owner);
        content.put(DATA,data);
        content.put(STATUS_CODE,statusCode);
        content.put(CREATION_DATE, date);
        content.put(UPDATED_DATE, date);
        db.insert(CARD_TABLE_NAME,null,content);
        return id;
    }

    public String createFcm(String fcm){
        String id = UUID.randomUUID().toString();
        String date = new Date().toString();
        ContentValues content = new ContentValues();
        content.put(ID, id);
        content.put(FCM_TOKEN,fcm);
        content.put(CREATION_DATE, date);
        content.put(UPDATED_DATE, date);
        db.insert(FCM_TABLE_NAME,null,content);
        return id;
    }

    public int returnFcmCount()
    {
        return (int) DatabaseUtils.queryNumEntries(db, FCM_TABLE_NAME);
    }

    public int returnAmountOfAvailableCards()
    {
        return (int) DatabaseUtils.queryNumEntries(db, AVAILABLE_CARD_TABLE_NAME);
    }

    public int returnAmountOfProfiles()
    {
        return (int) DatabaseUtils.queryNumEntries(db, PROFILE_TABLE_NAME);
    }

    public List<Profile> returnProfiles()
    {
        Cursor cursor =  db.query(PROFILE_TABLE_NAME, new String[]{ID, PROFILE_NAME,PROFILE_IMG,HASH,SALT,SERVER_ID,PRIVATE_KEY,PUBLIC_KEY}, null, null, null, null, null);
        List<Profile> profiles = new ArrayList<>();
        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
                profiles.add(new Profile(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                ));
            }
        }
        return profiles;
    }

    public String returnFcmToken(){
        Cursor cursor =  db.query(FCM_TABLE_NAME, new String[]{FCM_TOKEN}, null, null, null, null, null);
        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
                return cursor.getString(0);
            }
        }
        return null;
    }

    private String getFcmTokenId(){
        Cursor cursor =  db.query(FCM_TABLE_NAME, new String[]{ID}, null, null, null, null, null);
        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
                return cursor.getString(0);
            }
        }
        return null;
    }

    public boolean updateFcm(String fcm)
    {
        String id = getFcmTokenId();
        ContentValues content = new ContentValues();
        content.put(FCM_TOKEN,fcm);
        db.update(FCM_TABLE_NAME,content,ID + " = ?", new String[] { id });
        return true;
    }

    public int returnAmountOfCards()
    {
        return (int) DatabaseUtils.queryNumEntries(db, CARD_TABLE_NAME);
    }

    public boolean updateCardData(String id,String data)
    {
        ContentValues content = new ContentValues();
        content.put(DATA,data);
        db.update(CARD_TABLE_NAME,content,ID + " = ?", new String[] { id });
        return true;
    }

    public boolean updateCardImg(String id,String img)
    {
        ContentValues content = new ContentValues();
        content.put(COVER_IMG_URL,img);
        db.update(CARD_TABLE_NAME,content,ID + " = ?", new String[] { id });
        return true;
    }

    public boolean updateProfileName(String id,String name)
    {
        ContentValues content = new ContentValues();
        content.put(PROFILE_NAME,name);
        db.update(PROFILE_TABLE_NAME,content,ID + " = ?", new String[] { id });
        return true;
    }

    public boolean updateProfileSecurity(String id,String salt, String hash)
    {
        ContentValues content = new ContentValues();
        content.put(SALT,salt);
        content.put(HASH,hash);
        db.update(PROFILE_TABLE_NAME,content,ID + " = ?", new String[] { id });
        return true;
    }

    public void removeCard(String id)
    {
        db.delete(CARD_TABLE_NAME, ID + " = ?", new String[]{id});
    }

    public void removeProfile(String id)
    {
        db.delete(PROFILE_TABLE_NAME, ID + " = ?", new String[]{id});
    }

    public void removeAvailableCard(String id)
    {
        db.delete(AVAILABLE_CARD_TABLE_NAME, ID + " = ?", new String[]{id});
    }
}
