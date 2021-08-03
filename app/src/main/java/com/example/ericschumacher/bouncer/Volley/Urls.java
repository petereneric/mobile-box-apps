package com.example.ericschumacher.bouncer.Volley;

public class Urls {
    //private final static String URL_REST_API = "http://svp-server.com/svp-gmbh/erp/bouncer/src/api.php/";
    public final static String URL_REST_API = "https://www.svp-api.com/api/public/index.php/";

    public final static String URL_GET_MANUFACTURER_ALL = URL_REST_API+"manufacturer/all";
    public final static String URL_GET_CHARGER_ALL = URL_REST_API+"chargers/all/new";
    public final static String URL_GET_MODEL_BY_TAC = URL_REST_API+"model/tac/";
    public final static String URL_GET_MODEL_BY_NAME = URL_REST_API+"model/name/";
    public final static String URL_POST_MODEL_BY_NAME = URL_REST_API+"model/name";
    public final static String URL_GET_MODEL_BY_ID = URL_REST_API+"model/id/";// needs to be created
    public final static String URL_GET_MODELS_BY_NAMEPART = URL_REST_API+"models/";
    public final static String URL_POST_MODELS_BY_NAMEPART = URL_REST_API+"models/name_part";
    public final static String URL_UPDATE_MODEL = URL_REST_API+"model/update";
    public final static String URL_UPDATE_MODEL_BATTERY = URL_REST_API+"model/battery/update";
    public final static String URL_GET_DEVICE_BY_ID = URL_REST_API+"device/id/";
    public final static String URL_GET_DEVICE_BY_IMEI = URL_REST_API+"device/imei/";
    public final static String URL_PUT_MODEL_ADD = URL_REST_API+"model/add/";
    public final static String URL_GET_DEVICES = URL_REST_API+"devices";
    public final static String URL_GET_COLORS_MODEL = URL_REST_API+"colors/";
    public final static String URL_UPLOAD_IMAGE_MODEL_COLOR = URL_REST_API+"upload/images/device/";
    public final static String URL_PUT_MODEL_TAC = URL_REST_API+"model/tac/";
    //public final static String URL_PUT_MODEL_TAC = "https://www.svp-api.com/api/public/index.php/model/tac/";
    public final static String URL_MODELS_MERGE = URL_REST_API+"models/merge/";
    public final static String URL_GET_MODELS = URL_REST_API+"models";
    public final static String URL_GET_MARKETING_SHIPPING_RECORDS = URL_REST_API+"marketing_shipping_records";
    public final static String URL_UPDATE_MARKETING_SHIPPING = URL_REST_API+"marketing_shipping/update";
    public final static String URL_GET_BATTERY_EXPLOITATION = URL_REST_API+"battery/exploitation/";
    public final static String URL_GET_BATTERY_BY_NAME = URL_REST_API+"battery/name/";
    public final static String URL_GET_BATTERY_BY_NAME_PART = URL_REST_API+"battery/name_part/";
    public final static String URL_GET_BATTERY_CONNECT_MODEL = URL_REST_API+"model/battery/connect/";
    public final static String URL_PUT_BATTERY_ADD = URL_REST_API+"battery/add/";
    public final static String URL_UPDATE_UNIT_BACKCOVER = URL_REST_API+"unit_backcover/update";
    public final static String URL_UPDATE_UNIT_BATTERY = URL_REST_API+"unit_battery/update";
    public final static String URL_INCREMENT_RECORD_RECYCLING = URL_REST_API+"record/recycling/";
    public final static String URL_INCREMENT_RECORD_REUSE = URL_REST_API+"record/reuse/";
    public final static String URL_GET_MODEL_DAMAGE = URL_REST_API+"model/damage/";
    public final static String URL_GET_DEVICE_DAMAGE = URL_REST_API+"device/damage/";
    public final static String URL_GET_BATTERIES_BY_MANUFACTURER = URL_REST_API+"battery/manufacturer/";
    public final static String URL_GET_BATTERIES_BY_NAME_PART = URL_REST_API+"battery/name_part/";
    public final static String URL_DELETE_BATTERY = URL_REST_API+"battery/delete/";
    public final static String URL_UPDATE_BATTERY = URL_REST_API+"battery/update";
    public final static String URL_MERGE_BATTERY = URL_REST_API+"battery/merge";
    public final static String URL_GET_CHARGER_BY_MANUFACTURER = URL_REST_API+"charger/manufacturer/";
    public final static String URL_PUT_MODEL_BATTERY_UPDATE = URL_REST_API+"model_battery/update";
    public final static String URL_GET_MODEL_BATTERY_BY_MODEL = URL_REST_API+"model_battery/model/";
    public final static String URL_GET_COLLECTOR_BY_NAMEPART = URL_REST_API+"collector/namepart/";
    public final static String URL_GET_RECORDS_INPROGRESS = URL_REST_API+"record/inprogress";
    public final static String URL_GET_INFORMATION_STOCK_PRIME_INFORMATION = URL_REST_API+"information/stock_prime";
    public final static String URL_PUT_STOCK_PRIME_HIGHEST_LKU = URL_REST_API+"stock_prime/highest_lku/";
    public final static String URL_GET_STOCK_PRIME_LKU_SUGGESTION = URL_REST_API+"stock_prime/lku/suggestion/";
    public final static String URL_GET_ARTICLE = URL_REST_API+"article/";
    public final static String URL_POST_ID_MODEL_COLOR_SHAPE_FOR_JUICER = URL_REST_API+"id_model_color_shape/juicer";
    public final static String URL_GET_DEVICES_BY_ID_MODEL_COLOR_SHAPE_FOR_JUICER = URL_REST_API+"devices/juicer/";
    public final static String URL_GET_DEVICES_IN_STOCK = URL_REST_API+"devices/stock/";
    public final static String URL_GET_ARTICLE_IMAGE_MAIN = URL_REST_API+"article/image/main/";
    public final static String URL_GET_DEVICE_IMAGE_MAIN = URL_REST_API+"device/image/main/";
    public final static String URL_PUT_ARTICLE_BOOKING_INTO = URL_REST_API+"article/booking/into/";
    public final static String URL_GET_RECORD_BY_ID = URL_REST_API+"record/id/";
    public final static String URL_ADD_DEVICE = URL_REST_API+"device/add/2";
    public final static String URL_RECORD_FINISH = URL_REST_API+"record/selected/";
    public final static String URL_POST_MODELS_UNKNOWN = URL_REST_API+"models/unknown";
    public final static String URL_GET_ARTICLE_IMAGE_MAIN_BY_MODEL_COLOR= URL_REST_API+"article/image/main/";
    public final static String URL_PUT_BOX_UPDATE= URL_REST_API+"box/update";
    public final static String URL_PUT_BOX_ADD= URL_REST_API+"box/add";
    public final static String URL_DELETE_BOX_DELETE= URL_REST_API+"box/delete/";
    public final static String URL_GET_BOX_BY_ID= URL_REST_API+"box/";
    public final static String URL_GET_COLLECTORS_BY_INPUT= URL_REST_API+"collectors/input/";
    public final static String URL_PUT_UPDATE_RECORD= URL_REST_API+"record/update";
    public final static String URL_RECORD_ADD= URL_REST_API+"record/add";
    public final static String URL_GET_RECORD_BY_SHIPPINGNUMBER= URL_REST_API+"record/shippingnumber/";

    public final static String URL_RECORD_DELETE= URL_REST_API+"record/delete/";
    public final static String URL_PUT_STOCK_EXCESS_LKU_FULL= URL_REST_API+"stock/excess/lku/full/";
    public final static String URL_GET_DHL_INTERN_CREATE_SHIPMENT_ORDER = URL_REST_API+"dhl/intern/createShipmentOrder/";
    public final static String URL_POST_ORDER_MAIL = URL_REST_API+"order/mail/";
    public final static String URL_UPDATE_DEVICE = URL_REST_API+"device/update/2";

    // Check
    public final static String URL_GET_CHECKS = URL_REST_API+"team/checks";
    public final static String URL_GET_MODEL_CHECKS = URL_REST_API+"team/model/checks/";
    public final static String URL_GET_MODEL_CHECKS_AVAILABLE = URL_REST_API+"team/model/checks/available/";
    public final static String URL_CREATE_MODEL_CHECK = URL_REST_API+"team/model/check/";
    public final static String URL_UPDATE_MODEL_CHECK = URL_REST_API+"team/model/check";
    public final static String URL_DELETE_MODEL_CHECK = URL_REST_API+"team/model/check/";
    public final static String URL_GET_DIAGNOSES = URL_REST_API+"team/diagnoses/";
    public final static String URL_GET_DIAGNOSE = URL_REST_API+"team/diagnose/";
    public final static String URL_UPDATE_DIAGNOSE = URL_REST_API+"team/diagnose";
    public final static String URL_CREATE_DIAGNOSE = URL_REST_API+"team/diagnose";
    public final static String URL_DELETE_DIAGNOSE = URL_REST_API+"team/diagnose/";
    public final static String URL_CREATE_DIAGNOSE_CHECK = URL_REST_API+"team/diagnose/check";
    public final static String URL_UPDATE_DIAGNOSE_CHECK = URL_REST_API+"team/diagnose/check";
    public final static String URL_DELETE_DIAGNOSE_CHECK = URL_REST_API+"team/diagnose/check/";
    public final static String URL_GET_DIAGNOSE_CHECKS = URL_REST_API+"team/diagnose/checks/";

}
