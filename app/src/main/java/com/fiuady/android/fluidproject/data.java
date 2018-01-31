package com.fiuady.android.fluidproject;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.fiuady.android.fluidproject.tables.lastDateConfigure;
import com.fiuady.android.fluidproject.tables.lastHumidity;
import com.fiuady.android.fluidproject.tables.lastPumpOn;

class  lastDateConfigureCursor extends CursorWrapper {
   public lastDateConfigureCursor (Cursor cursor) {super(cursor);}

    public lastDateConfigure getLastDateConfigure() {
        Cursor cursor = getWrappedCursor();
        return new lastDateConfigure(cursor.getInt(cursor.getColumnIndex(DataSchema.lastDateConfigureTable.DAY)),
                cursor.getInt(cursor.getColumnIndex(DataSchema.lastDateConfigureTable.MONTH)),
                cursor.getInt(cursor.getColumnIndex(DataSchema.lastDateConfigureTable.YEAR)),
                cursor.getInt(cursor.getColumnIndex(DataSchema.lastDateConfigureTable.HOUR)),
                cursor.getInt(cursor.getColumnIndex(DataSchema.lastDateConfigureTable.MINUTE)),
                cursor.getInt(cursor.getColumnIndex(DataSchema.lastDateConfigureTable.EACH)));
    }
}

class lastPumpOnCursor extends CursorWrapper {
    public lastPumpOnCursor (Cursor cursor) {super(cursor);}

    public lastPumpOn getLastPumpOn () {
        Cursor cursor = getWrappedCursor();
        return new lastPumpOn(cursor.getInt(cursor.getColumnIndex(DataSchema.lastPumpOnTable.DAY)),
                cursor.getInt(cursor.getColumnIndex(DataSchema.lastPumpOnTable.MONTH)),
                cursor.getInt(cursor.getColumnIndex(DataSchema.lastPumpOnTable.YEAR)),
                cursor.getInt(cursor.getColumnIndex(DataSchema.lastPumpOnTable.HOUR)),
                cursor.getInt(cursor.getColumnIndex(DataSchema.lastPumpOnTable.MINUTE)));
    }
}

class lastHumidityCursor extends CursorWrapper {
    public lastHumidityCursor (Cursor cursor) {super(cursor);}

    public lastHumidity getLastHumidity() {
        Cursor cursor = getWrappedCursor();
        return new lastHumidity(cursor.getInt(cursor.getColumnIndex(DataSchema.lastHumidityTable.PLANT1)),
                cursor.getInt(cursor.getColumnIndex(DataSchema.lastHumidityTable.PLANT2)));
    }
}

public final class data {
    private Helper helper;
    private SQLiteDatabase db;

    public data (Context context) {
        helper = new Helper(context);
        db = helper.getWritableDatabase();
    }

    public lastDateConfigure getLastConfigureData () {
        lastDateConfigureCursor cursor = new lastDateConfigureCursor(db.rawQuery(" SELECT* FROM LastDateConfigure", null));
        cursor.moveToFirst();

        lastDateConfigure date = cursor.getLastDateConfigure();

        cursor.close();
        return date;
    }

    public lastHumidity getLastHumidity() {
        lastHumidityCursor cursor = new lastHumidityCursor(db.rawQuery(" SELECT* FROM lastConfigurationOfHumidity", null));
        cursor.moveToFirst();

        lastHumidity humidity = cursor.getLastHumidity();

        cursor.close();
        return humidity;
    }

    public lastPumpOn getLastPumpOn() {
        lastPumpOnCursor cursor = new lastPumpOnCursor(db.rawQuery("SELECT* FROM lastPumpOn", null));
        cursor.moveToFirst();

        lastPumpOn pumpOn = cursor.getLastPumpOn();
        cursor.close();

        return pumpOn;
    }

    public void updateLastDateConfigure (int day, int month, int year, int hour, int minute, int each) {
        db.execSQL("UPDATE LastDateConfigure \n" +
                "  SET day = " + day);

        db.execSQL("UPDATE LastDateConfigure \n" +
                "  SET month = " + month);

        db.execSQL("UPDATE LastDateConfigure \n" +
                "  SET year = " + year);

        db.execSQL("UPDATE LastDateConfigure \n" +
                "  SET hour = " + hour);

        db.execSQL("UPDATE LastDateConfigure \n" +
                "  SET minute = " + minute);

        db.execSQL("UPDATE LastDateConfigure \n" +
                "  SET each = " + each);
    }

    public void updateLastPumpOn (String day, String month, String year, String hour, String minute) {
        db.execSQL("UPDATE lastPumpOn \n" +
                "  SET day = " + day);

        db.execSQL("UPDATE lastPumpOn \n" +
                "  SET month = " + month);

        db.execSQL("UPDATE lastPumpOn \n" +
                "  SET year = " + year);

        db.execSQL("UPDATE lastPumpOn \n" +
                "  SET hour = " + hour);

        db.execSQL("UPDATE lastPumpOn \n" +
                "  SET minute = " + minute);
    }

    public void modifyP(int planta, int nplant) {

        if(nplant == 1) {
            db.execSQL("UPDATE lastConfigurationOfHumidity SET plant1 = " + planta);
        }

        if (nplant == 2) {
            db.execSQL("UPDATE lastConfigurationOfHumidity SET plant2 = " + planta);
        }
    }
}
