package com.forkan.devicecurrentlocation;

import io.realm.Realm;


import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

//under dbhelper package.
public class AppDatabase {


    public AppDatabase() {
    }

    public static void saveDistance(Distance distance) {
        Realm.init(Realm.getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(distance);
        realm.commitTransaction();
    }

//    public static Distance getDistance() {
//        Realm.init(getApplicationContext());
//        Realm realm = Realm.getDefaultInstance();
//        return realm.where(Distance.class).findFirst();
//    }
}
