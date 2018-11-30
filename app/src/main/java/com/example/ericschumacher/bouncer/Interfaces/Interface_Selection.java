package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Collection.Record;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public interface Interface_Selection {
    void showResult();
    void afterBounce();

    void showFragmentRecordNew();
    void showFragmentRecordExisting();
    void showFragmentRecordMenu();
    void setRecord(Record record);
    void pauseRecord();
    void finishRecord();
    void deleteRecord();
    int getCountReuse();
    int getCountRecycling();
    String getNameCollector();
}
