package com.blesk.messagingservice.Value;

public class Messages {

    public static final String SEARCH_ERROR = "Kritériám nevyhoveli žiadné záznamy";
    public static final String PAGINATION_ERROR = "Požiadavku sa nepodarilo spracovať, chyba stránkovania";



    public static final String TYPE_MISMATCH_EXCEPTION = "Nesprávný formát URL adresi";
    public static final String REQUEST_BODY_NOT_FOUND_EXCEPTION = "Prázdna požiadavka";
    public static final String PAGE_NOT_FOUND_EXCEPTION = "Je nám ľúto, ale požadovaná stránka nebola nájdená";
    public static final String NULL_POINTER_EXCEPTION = "Ľutujeme, ale nastala chyba";
    public static final String AUTH_EXCEPTION = "Ľutujeme, ale stránka nie je k dispozícií";
    public static final String AUTH_REQUIRED_EXCEPTION = "Prístup odmietnutý";
    public static final String SQL_EXCEPTION = "Operácia sa neuskutočnila";
    public static final String FIREBASE_EXCEPTION = "Inicializácia Firebase bola neúspešná";
    public static final String EXCEPTION = "Nastala neočakávaná chyba";

    public static final String CONVERSATION_PARTICIPANTS_NOT_NULL = "Nezadali ste účastníkov konverzácie";

    public static final String STATUS_USER_NAME_NOT_NULL = "Nezadali ste používateľské meno";
    public static final String STATUS_TOKEN_NOT_NULL = "Chyba v inicializácií FCM tokenu";
    public static final String STATUS_USER_NAME_SIZE = "Používateľské meno je príliž krátke alebo dlhé";
    public static final String STATUS_STATE_NOT_NULL = "Nezadali ste status používateľa";

    public static final String CONVERSATION_USERS_ACCOUNT_ID_NOT_NULL = "Nezadali ste identifikačné číslo účastníkov konverzácie";
    public static final String CONVERSATION_STATUS_ID_NOT_NULL = "Nezadali ste identifikačné číslo statusu používateľa";
    public static final String CONVERSATION_USERS_USER_NAME_NOT_NULL = "Nezadali ste používatelské meno účastníkov konverzácie";

    public static final String COMMUNICATION_USER_NAME_NOT_NULL = "Nezadali ste používateľské meno";
    public static final String COMMUNICATION_USER_NAME_SIZE = "Používateľské meno je príliž krátke alebo dlhé";
    public static final String COMMUNICATION_SENDER_NOT_NULL = "Nezadali ste identifikačné číslo použivateľského konta";
    public static final String COMMUNICATION_SENDER_POSITIVE = "Neplatné indentikacné číslo použivateľského konta";
    public static final String COMMUNICATION_CONTENT_NOT_NULL = "Obsah kommunikácie je prazdný";
    public static final String COMMUNICATION_CONTENT_SIZE = "Obsah kommunikácie je príliž krátky alebo dlhý";
    public static final String COMMUNICATION_CONVERSATIONS_NOT_NULL = "Správa nie je priradená žiadnej konverzácie";



    public static final String CREATE_COMMUNICATION = "Nepodarilo sa vytvoriť správu";
    public static final String DELETE_COMMUNICATION = "Nepodarilo sa odstrániť správu";
    public static final String UPDATE_COMMUNICATION = "Nepodarilo sa aktualizovať správu";
    public static final String GET_COMMUNICATION = "Žiadná správa nebola najdená";
    public static final String GET_ALL_COMMUNICATIONS = "Nenašli sa žiadné správy";

    public static final String CREATE_CONVERSATION = "Nepodarilo sa vytvoriť konverzáciu";
    public static final String DELETE_CONVERSATION = "Nepodarilo sa odstrániť konverzáciu";
    public static final String UPDATE_CONVERSATION = "Nepodarilo sa aktualizovať konverzáciu";
    public static final String GET_CONVERSATION = "Žiadná konverzácia nebola najdená";
    public static final String GET_ALL_CONVERSATIONS = "Nenašli sa žiadné konverzácie";

    public static final String CREATE_STATUS = "Nepodarilo sa vytvoriť status použivateľa";
    public static final String DELETE_STATUS = "Nepodarilo sa odstrániť status použivateľa";
    public static final String UPDATE_STATUS = "Nepodarilo sa aktualizovať status použivateľa";
    public static final String GET_STATUS = "Status používateľa nebol nájdení";
    public static final String GET_ALL_STATUSES = "Ziadný status používateľa nebol najdení";
}