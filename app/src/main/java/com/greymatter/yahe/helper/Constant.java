package com.greymatter.yahe.helper;

import java.util.HashMap;

public class Constant {
    //MODIFICATION PART

  //Admin panel url
  public static final String MainBaseUrl = "https://yahe.greymatterworks.in/";

  //Admin panel url or ecart multivendor website url (if exist)
  public static final String WebSiteUrl = "https://ecartmultivendorweb.thewrteam.in/";

    //set your jwt secret key here...key must same in PHP and Android
    public static final String JWT_KEY = "jp@0111";

    //Category View Number Of Grid Per Line
    public static final int GRID_COLUMN = 3;

    //Load items limit in listing ,Maximum load item once
    public static final int LOAD_ITEM_LIMIT = 10;

    //set true if you want to show sellers in home page, set false if you want to hide the seller in home page
    public static final boolean SHOW_SELLERS_IN_HOME_PAGE = true;


    //MODIFICATION PART END

    public static final String BaseUrl = MainBaseUrl + "api-firebase/";

    //Do not change anything in this link**************************************************
    public static final String PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=";
    public static final String PLAY_STORE_RATE_US_LINK = "market://details?id=";

    //*************************************************************************************
    //PayTm configs
    public static final String WEBSITE_LIVE_VAL = "WEB";
    public static final String INDUSTRY_TYPE_ID_LIVE_VAL = "Retail";
    public static final String MOBILE_APP_CHANNEL_ID_LIVE_VAL = "WAP";
    //    public static final String PAYTM_ORDER_PROCESS_LIVE_URL = "https://securegw.paytm.in/order/process";
    public static final String WEBSITE_DEMO_VAL = "WEBSTAGING";
    public static final String INDUSTRY_TYPE_ID_DEMO_VAL = "Retail";
    public static final String MOBILE_APP_CHANNEL_ID_DEMO_VAL = "WAP";
    public static final String PAYTM_ORDER_PROCESS_DEMO_VAL = "https://securegw-stage.paytm.in/order/process";
    public static final String GENERATE_PAYTM_CHECKSUM = MainBaseUrl + "paytm/generate-checksum.php";
    public static final String VALID_TRANSACTION = MainBaseUrl + "/paytm/valid-transction.php";
    public static final String CALLBACK_URL = "CALLBACK_URL";
    public static final String CHECKSUMHASH = "CHECKSUMHASH";
    public static final String ORDER_ID_ = "ORDER_ID";
    public static final String CHANNEL_ID = "CHANNEL_ID";
    public static final String INDUSTRY_TYPE_ID = "INDUSTRY_TYPE_ID";
    public static final String WEBSITE = "WEBSITE";
    public static final String TXN_AMOUNT = "TXN_AMOUNT";
    public static final String MID = "MID";


    //**********APIS**********
    public static final String FAQ_URL = BaseUrl + "get-faqs.php";
    public static final String INVOICE_URL = MainBaseUrl + "invoice-for-mobile.php";
    public static final String CATEGORY_URL = BaseUrl + "get-categories.php";
    public static final String SELLER_URL = BaseUrl + "get-seller-data.php";
    public static final String GET_RAZORPAY_ORDER_URL = BaseUrl + "create-razorpay-order.php";
    public static final String GET_SUB_CATEGORY_URL = BaseUrl + "get-subcategories.php";
    public static final String GET_SECTION_URL = BaseUrl + "sections.php";
    public static final String GET_ADDRESS_URL = BaseUrl + "user-addresses.php";
    public static final String REGISTER_URL = BaseUrl + "user-registration.php";
    public static final String PAPAL_URL = MainBaseUrl + "paypal/create-payment.php";
    public static final String LOGIN_URL = BaseUrl + "login.php";
    public static final String GET_ALL_DATA_URL = BaseUrl + "get-all-data.php";
    public static final String GET_PRODUCTS_URL = BaseUrl + "get-products.php";
    public static final String GET_SELLER_DATA_URL = BaseUrl + "get-seller-data.php";
    public static final String SETTING_URL = BaseUrl + "settings.php";
    public static final String GET_FAVORITES_URL = BaseUrl + "favorites.php";
    public static final String MIDTRANS_PAYMENT_URL = MainBaseUrl + "midtrans/create-payment.php";
    public static final String GET_LOCATIONS_URL = BaseUrl + "get-locations.php";
    public static final String ORDER_PROCESS_URL = BaseUrl + "order-process.php";
    public static final String USER_DATA_URL = BaseUrl + "get-user-data.php";
    public static final String CART_URL = BaseUrl + "cart.php";
    public static final String STRIPE_BASE_URL = MainBaseUrl + "stripe/create-payment.php";
    public static final String TRANSACTION_URL = BaseUrl + "get-user-transactions.php";
    public static final String PROMO_CODE_CHECK_URL = BaseUrl + "validate-promo-code.php";
    public static final String VERIFY_PAYMENT_REQUEST = BaseUrl + "payment-request.php";
    public static final String REGISTER_DEVICE_URL = BaseUrl + "store-fcm-id.php";


    //**************parameters***************
    public static final String VERIFY_PAYSTACK = "verify_paystack_transaction";
    public static final String AccessKey = "accesskey";
    public static final String AccessKeyVal = "90336";
    public static final String PROFILE = "profile";
    public static final String UPLOAD_PROFILE = "upload_profile";
    public static final String GetVal = "1";
    public static final String GET_PIN_CODES = "get_pincodes";
    public static final String GET_CITIES = "get_cities";
    public static final String GET_AREAS = "get_areas";
    public static final String GROSS_AMOUNT = "gross_amount";
    public static final String AUTHORIZATION = "Authorization";
    public static final String PARAMS = "params";
    public static final String GET_SELECTED_PINCODE = "get_selected_pincode";
    public static final String GET_SELECTED_PINCODE_NAME = "get_selected_pincode_name";
    public static final String GET_SELECTED_PINCODE_ID = "get_selected_pincode_id";
    public static final String IS_USER_LOGIN = "is_user_login";
    public static final String GET_PRIVACY = "get_privacy";
    public static final String GET_TERMS = "get_terms";
    public static final String GET_ADDRESSES = "get_addresses";
    public static final String DELETE_ADDRESS = "delete_address";
    public static final String ADD_ADDRESS = "add_address";
    public static final String UPDATE_ADDRESS = "update_address";
    public static final String GET_CONTACT = "get_contact";
    public static final String GET_ABOUT_US = "get_about_us";
    public static final String NEW_BALANCE = "new_balance";
    public static final String ADD_TO_FAVORITES = "add_to_favorites";
    public static final String REMOVE_FROM_FAVORITES = "remove_from_favorites";
    public static final String CANCELLED = "cancelled";
    public static final String RECEIVED = "received";
    public static final String SHIPPED = "shipped";
    public static final String PROCESSED = "processed";
    public static final String DELIVERED = "delivered";
    public static final String GET_NOTIFICATIONS = "get-notifications";
    public static final String RETURNED = "returned";
    public static final String GET_USER_DATA = "get_user_data";
    public static final String BALANCE = "balance";
    public static final String AWAITING_PAYMENT = "awaiting_payment";
    public static final String KEY_WALLET_USED = "wallet_used";
    public static final String WALLET_BALANCE = "wallet_balance";
    public static final String WALLET = "wallet";
    public static final String PAYMENT = "payment";
    public static final String REDIRECT_URL = "redirect_url";
    public static final String URL = "url";
    public static final String ADD_MULTIPLE_ITEMS = "add_multiple_items";
    public static final String SAVE_FOR_LATER_ITEMS = "save_for_later_items";
    public static final String GET_REORDER_DATA = "get_reorder_data";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String PAYER_EMAIL = "payer_email";
    public static final String COUNTRY_CODE = "country_code";
    public static final String COUNTRY = "country";
    public static final String IS_DEFAULT = "is_default";
    public static final String ITEM_NAME = "item_name";
    public static final String ITEM_NUMBER = "item_number";
    public static final String UPDATE_ORDER_STATUS = "update_order_status";
    public static final String ORDER_ITEM_ID = "order_item_id";
    public static final String PAYMENT_METHODS = "payment_methods";
    public static final String PAY_M_KEY = "payumoney_merchant_key";
    public static final String PAYU_M_ID = "payumoney_merchant_id";
    public static final String PAYU_SALT = "payumoney_salt";
    public static final String RAZOR_PAY_KEY = "razorpay_key";
    public static final String paystack_public_key = "paystack_public_key";
    public static final String UNREAD_NOTIFICATION_COUNT = "unread_notification_count";
    public static final String UNREAD_WALLET_COUNT = "unread_wallet_count";
    public static final String UNREAD_TRANSACTION_COUNT = "unread_transaction_count";
    public static final String flutterwave_public_key = "flutterwave_public_key";
    public static final String flutterwave_secret_key = "flutterwave_secret_key";
    public static final String flutterwave_encryption_key = "flutterwave_encryption_key";
    public static final String flutterwave_currency_code = "flutterwave_currency_code";
    public static final String CITY_ID = "city_id";
    public static final String PINCODE_ID = "pincode_id";
    public static final String PINCODE = "pincode";
    public static final String CITY = "city";
    public static final String AREA_ID = "area_id";
    public static final String REFERRAL_CODE = "referral_code";
    public static final String FRIEND_CODE = "friends_code";
    public static final String SOLD_OUT_TEXT = "Sold Out";
    public static final String QTY = "qty";
    public static final String GET_USER_CART = "get_user_cart";
    public static final String DELETE_ORDER = "delete_order";
    public static final String GET_USER_TRANSACTION = "get_user_transactions";
    public static final String TYPE_TRANSACTION = "transactions";
    public static final String TYPE_WALLET_TRANSACTION = "wallet_transactions";
    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    public static final String PENDING = "pending";
    public static final String CREDIT = "credit";
    public static final String SAVE_FOR_LATER = "save_for_later";
    public static final String REMOVE_FROM_CART = "remove_from_cart";
    public static final String SORT = "sort";
    public static final String TYPE = "type";
    public static final String IMAGE = "image";
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final String VARIANT_POSITION = "variantPosition";
    public static final String LIST_POSITION = "list_position";
    public static final String SUBTITLE = "subtitle";
    public static final String PRODUCTS = "products";
    public static final String STATUS = "status";
    public static final String DATE_ADDED = "date_added";
    public static final String TITLE = "title";
    public static final String SECTION_STYLE = "style";
    public static final String SHORT_DESC = "short_description";
    public static final String REGISTER = "register";
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String LOGIN = "login";
    public static final String ALTERNATE_MOBILE = "alternate_mobile";
    public static final String PASSWORD = "password";
    public static final String FCM_ID = "fcm_id";
    public static final String GET_ALL_PRODUCTS_NAME = "get_all_products_name";
    public static final String GET_ALL_PRODUCTS = "get_all_products";
    public static final String CHECK_DELIEVABILITY = "check_deliverability";
    public static final String STATE = "state";
    public static final String ERROR = "error";
    public static final String STORE_NAME = "store_name";
    public static final String LOGO = "logo";
    public static final String GET_TIMEZONE = "get_timezone";
    public static final String ORDER_NOTE = "order_note";
    public static final String VERIFY_USER = "verify-user";
    public static final String USER_ID = "user_id";
    public static final String GET_PROMO_CODES = "get_promo_codes";
    public static final String OTP = "otp";
    public static final String ADD_WALLET_BALANCE = "add_wallet_balance";
    public static final String TAX_AMOUNT = "tax_amount";
    public static final String TAX_PERCENT = "tax_percentage";
    public static final String RETURN_DAYS = "return_days";
    public static final String SELLER_ID = "seller_id";
    public static final String GET_SELLER_DATA = "get_seller_data";
    public static final String EDIT_PROFILE = "edit-profile";
    public static final String CHANGE_PASSWORD = "change-password";
    public static final String FORGOT_PASSWORD_MOBILE = "forgot-password-mobile";
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORIES = "categories";
    public static final String SLIDER_IMAGES = "slider_images";
    public static final String SELLER = "seller";
    public static final String SECTIONS = "sections";
    public static final String OFFER_IMAGES = "offer_images";
    public static final String RETURN_STATUS = "return_status";
    public static final String CANCELLABLE_STATUS = "cancelable_status";
    public static final String TILL_STATUS = "till_status";
    public static final String SUB_CATEGORY_ID = "subcategory_id";
    public static final String GET_ALL_SECTIONS = "get-all-sections";
    public static final String SECTION_ID = "section_id";
    public static final String GET_FAVORITES = "get_favorites";
    public static final String GET_PRODUCTS_OFFLINE = "get_products_offline";
    public static final String GET_VARIANTS_OFFLINE = "get_variants_offline";
    public static final String SEARCH = "search";
    public static final String ADD_TRANSACTION = "add_transaction";
    public static final String GET_PAYMENT_METHOD = "get_payment_methods";
    public static final String GET_ORDERS = "get_orders";
    public static final String CONTACT = "contact";
    public static final String DATA = "data";
    public static final String ITEMS = "items";
    public static final String PRODUCT_ID = "product_id";
    public static final String GET_SIMILAR_PRODUCT = "get_similar_products";
    public static final String PRODUCT_IDs = "product_ids";
    public static final String VARIANT_IDs = "variant_ids";
    public static final String MEASUREMENT = "measurement";
    public static final String PRICE = "price";
    public static final String DISCOUNT = "discount";
    public static final String DISCOUNTED_PRICE = "discounted_price";
    public static final String SETTINGS = "settings";
    public static final String GET_TIME_SLOT_CONFIG = "get_time_slot_config";
    public static final String TIME_SLOT_CONFIG = "time_slot_config";
    public static final String IS_TIME_SLOTS_ENABLE = "is_time_slots_enabled";
    public static final String DELIVERY_STARTS_FROM = "delivery_starts_from";
    public static final String ALLOWED_DAYS = "allowed_days";
    public static final String paypal_method = "paypal_payment_method";
    public static final String payu_method = "payumoney_payment_method";
    public static final String razor_pay_method = "razorpay_payment_method";
    public static final String cod_payment_method = "cod_payment_method";
    public static final String product = "product";
    public static final String cod_mode = "cod_mode";
    public static final String paystack_method = "paystack_payment_method";
    public static final String flutterwave_payment_method = "flutterwave_payment_method";
    public static final String midtrans_payment_method = "midtrans_payment_method";
    public static final String stripe_payment_method = "stripe_payment_method";
    public static final String paytm_payment_method = "paytm_payment_method";
    public static final String ssl_commerce_payment_method = "ssl_commerce_payment_method";
    public static final String ssl_commerece_mode = "ssl_commerece_mode";
    public static final String ssl_commerece_store_id = "ssl_commerece_store_id";
    public static final String ssl_commerece_secret_key = "ssl_commerece_secret_key";
    public static final String direct_bank_transfer_method = "direct_bank_transfer_method";
    public static final String account_name = "account_name";
    public static final String account_number = "account_number";
    public static final String bank_name = "bank_name";
    public static final String bank_code = "bank_code";
    public static final String notes = "notes";
    public static final String paytm_merchant_id = "paytm_merchant_id";
    public static final String paytm_merchant_key = "paytm_merchant_key";
    public static final String paytm_mode = "paytm_mode";
    public static final String payumoney_mode = "payumoney_mode";
    public static final String MINIMUM_AMOUNT = "min_amount";
    public static final String DELIVERY_CHARGE = "delivery_charge";
    public static final String CURRENCY = "currency";
    public static final String GET_FAQS = "get_faqs";
    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String AMOUNT = "amount";
    public static final String REFERENCE = "reference";
    public static final String PROMO_DISCOUNT = "promo_discount";
    public static final String DISCOUNT_AMT = "discount_rupees";
    public static final String TOTAL = "total";
    public static final String PRODUCT_VARIANT_ID = "product_variant_id";
    public static final String QUANTITY = "quantity";
    public static final String USER_NAME = "user_name";
    public static final String DELIVERY_TIME = "delivery_time";
    public static final String ADDRESS_ID = "address_id";
    public static final String PAYMENT_METHOD = "payment_method";
    public static final String ACTIVE_STATUS = "active_status";
    public static final String ADDRESS = "address";
    public static final String ADDRESS_LINE1 = "address_line1";
    public static final String POSTAL_CODE = "postal_code";
    public static final String LANDMARK = "landmark";
    public static final String TRANS_ID = "txn_id";
    public static final String MESSAGE = "message";
    public static final String FINAL_TOTAL = "final_total";
    public static final String FROM = "from";
    public static final String ORDER_ID = "order_id";
    public static final String IMAGES = "image[]";
    public static final String DELETE_BANK_TRANSFER_ATTACHMENT = "delete_bank_transfers_attachment";
    public static final String UPLOAD_BANK_TRANSFER_ATTACHMENT = "upload_bank_transfers_attachment";
    public static final String publishableKey = "publishableKey";
    public static final String clientSecret = "clientSecret";
    public static final String PLACE_ORDER = "place_order";
    public static final String NEW = "new";
    public static final String OLD = "old";
    public static final String HIGH = "high";
    public static final String LOW = "low";
    public static final String SUB_TOTAL = "sub_total";
    public static final String UNIT = "unit";
    public static final String SLUG = "slug";
    public static final String PROMO_CODE = "promo_code";
    public static final CharSequence[] filterValues = {" Newest to Oldest ", " Oldest to Newest ", " Price Highest to Lowest ", " Price Lowest to Highest "};
    public static final String CUST_ID = "CUST_ID";
    public static final String ORDERID = "ORDERID";
    public static final String STATUS_ = "STATUS";
    public static final String TXN_SUCCESS = "TXN_SUCCESS";
    public static final String minimum_version_required = "minimum_version_required";
    public static final String is_version_system_on = "is_version_system_on";
    public static final String min_order_amount = "min_order_amount";
    public static final String max_cart_items_count = "max_cart_items_count";
    public static final String area_wise_delivery_charge = "area_wise_delivery_charge";
    public static final String is_refer_earn_on = "is_refer_earn_on";
    public static final String refer_earn_bonus = "refer_earn_bonus";
    public static final String refer_earn_method = "refer_earn_method";
    public static final String max_refer_earn_amount = "max_refer_earn_amount";
    public static final String max_product_return_days = "max_product_return_days";
    public static final String user_wallet_refill_limit = "user_wallet_refill_limit";
    public static final String min_refer_earn_order_amount = "min_refer_earn_order_amount";
    public static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghjiklmnopqrstuvwxyz";
    public static final String NUMERIC_STRING = "123456789";
    public static String TOOLBAR_TITLE;

    //**************Constants Values***************
    public static String selectedAddressId = "";
    public static String DefaultAddress = "";
    public static String DefaultCity = "";
    public static String DefaultPinCode = "";
    public static boolean isCODAllow = true;
    public static Double SETTING_DELIVERY_CHARGE = 0.0;
    public static Double SETTING_TAX = 0.0;
    public static Double SETTING_MINIMUM_AMOUNT_FOR_FREE_DELIVERY = 0.0;
    public static String U_ID = "";
    public static HashMap<String, String> CartValues = new HashMap<>();
    public static int selectedDatePosition = 0;
    public static double FLOAT_TOTAL_AMOUNT = 0;
    public static int TOTAL_CART_ITEM = 0;
    public static boolean isOrderCancelled;
    public static String FRIEND_CODE_VALUE = "";
    public static String PAYPAL = "";
    public static String PAYUMONEY = "";
    public static String RAZORPAY = "";
    public static String COD = "";
    public static String COD_MODE = "";
    public static String PAYSTACK = "";
    public static String FLUTTERWAVE = "";
    public static String MIDTRANS = "";
    public static String STRIPE = "";
    public static String MERCHANT_ID = "";
    public static String MERCHANT_KEY = "";
    public static String PAYTM_MERCHANT_ID = "";
    public static String PAYTM = "";
    public static String PAYTM_MERCHANT_KEY = "";
    public static String PAYTM_MODE = "";
    public static String PAYUMONEY_MODE = "";
    public static String MERCHANT_SALT = "";
    public static String RAZOR_PAY_KEY_VALUE = "";
    public static String PAYSTACK_KEY = "";
    public static String FLUTTERWAVE_PUBLIC_KEY_VAL = "";
    public static String FLUTTERWAVE_SECRET_KEY_VAL = "";
    public static String FLUTTERWAVE_ENCRYPTION_KEY_VAL = "";
    public static String FLUTTERWAVE_CURRENCY_CODE_VAL = "";
    public static String SSLECOMMERZ = "";
    public static String SSLECOMMERZ_MODE = "";
    public static String SSLECOMMERZ_STORE_ID = "";
    public static String SSLECOMMERZ_SECRET_KEY = "";
    public static String DIRECT_BANK_TRANSFER = "";
    public static String ACCOUNT_NAME = "";
    public static String ACCOUNT_NUMBER = "";
    public static String BANK_NAME = "";
    public static String BANK_CODE = "";
    public static String NOTES = "";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String randomNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * NUMERIC_STRING.length());
            builder.append(NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}