package com.example.ericschumacher.bouncer.Volley;

public class Urls {
    private final static String URL_REST_API = "http://svp-server.com/svp-gmbh/erp/bouncer/src/api.php/";

    public final static String URL_GET_MANUFACTURER_ALL = URL_REST_API+"manufacturer/all";
    public final static String URL_GET_MODEL_BY_TAC = URL_REST_API+"model/tac/";
    public final static String URL_GET_MODEL_BY_NAME = URL_REST_API+"model/name/";
    public final static String URL_GET_MODEL_BY_ID = URL_REST_API+"model/id/";// needs to be created
    public final static String URL_GET_MODELS_BY_NAMEPART = URL_REST_API+"models/";
    public final static String URL_UPDATE_MODEL = URL_REST_API+"model/updateLayout";
    public final static String URL_UPDATE_MODEL_BATTERY = URL_REST_API+"model/battery/updateLayout";
    public final static String URL_GET_DEVICE_BY_ID = URL_REST_API+"device/id/";
    public final static String URL_GET_DEVICE_BY_IMEI = URL_REST_API+"device/imei/";
    public final static String URL_PUT_MODEL_ADD = URL_REST_API+"model/add/";
    public final static String URL_GET_DEVICES = URL_REST_API+"devices";
    public final static String URL_GET_COLORS_MODEL = URL_REST_API+"colors/";
    public final static String URL_UPLOAD_IMAGE_MODEL_COLOR = URL_REST_API+"upload/images/device/";
    public final static String URL_PUT_MODEL_TAC = URL_REST_API+"model/tac/";
    public final static String URL_MODELS_MERGE = URL_REST_API+"models/merge/";
    public final static String URL_GET_MODELS = URL_REST_API+"models";
    public final static String URL_GET_MARKETING_SHIPPING_RECORDS = URL_REST_API+"marketing_shipping_records";
    public final static String URL_UPDATE_MARKETING_SHIPPING = URL_REST_API+"marketing_shipping/updateLayout";
    public final static String URL_GET_BATTERY_EXPLOITATION = URL_REST_API+"battery/exploitation/";
    public final static String URL_GET_BATTERY_BY_NAME = URL_REST_API+"battery/name/";
    public final static String URL_GET_BATTERY_BY_NAME_PART = URL_REST_API+"battery/name_part/";
    public final static String URL_GET_BATTERY_CONNECT_MODEL = URL_REST_API+"model/battery/connect/";
    public final static String URL_PUT_BATTERY_ADD = URL_REST_API+"battery/add/";
    public final static String URL_UPDATE_UNIT_BACKCOVER = URL_REST_API+"unit_backcover/updateLayout";
    public final static String URL_UPDATE_UNIT_BATTERY = URL_REST_API+"unit_battery/updateLayout";
    public final static String URL_INCREMENT_RECORD_RECYCLING = URL_REST_API+"record/recycling/";
    public final static String URL_INCREMENT_RECORD_REUSE = URL_REST_API+"record/reuse/";
    public final static String URL_GET_MODEL_DAMAGE = URL_REST_API+"model/damage/";
    public final static String URL_GET_DEVICE_DAMAGE = URL_REST_API+"device/damage/";
    public final static String URL_GET_BATTERIES_BY_MANUFACTURER = URL_REST_API+"battery/manufacturer/";
    public final static String URL_GET_BATTERIES_BY_NAME_PART = URL_REST_API+"battery/name_part/";
    public final static String URL_DELETE_BATTERY = URL_REST_API+"battery/delete/";
    public final static String URL_UPDATE_BATTERY = URL_REST_API+"battery/updateLayout";
    public final static String URL_MERGE_BATTERY = URL_REST_API+"battery/merge";
    public final static String URL_GET_CHARGER_BY_MANUFACTURER = URL_REST_API+"charger/manufacturer/";
    public final static String URL_PUT_MODEL_BATTERY_UPDATE = URL_REST_API+"model_battery/updateLayout";
    public final static String URL_GET_MODEL_BATTERY_BY_MODEL = URL_REST_API+"model_battery/model/";
}
