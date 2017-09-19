package com.example.user.m_toto.Network;

import static com.example.user.m_toto.NurseNavigator.childIdImmunizationHold;

/**
 * Created by User on 5/12/2017.
 */

public class URLs {
    //BASE URL
   //public static final String BASE_URL = "http://172.16.97.66/mtoto/public/api/";
 public static final String BASE_URL = "http://192.168.43.124/mtoto/public/api/";

    //User login URL
    public static final String LOGIN_URL = BASE_URL + "login";

    //register mother
    public static final String REGISTER_MOTHER=BASE_URL+"registerMother";

    //register new born
    public static final String REGISTER_CHILD=BASE_URL+"registerChild";

    //list of mothers
    public static final String MOTHERS=BASE_URL+"mothers";

    //LIST OF CHILDREN
    public static final String CHILDREN=BASE_URL+"children";
 //LIST OF CHILDREN attached to mother
   public static final String MOTHERS_CHILDREN=BASE_URL+"getMothersChildren";

 //get height of child
 public static final String HEIGHT=BASE_URL+"height";

 //get weight of child
 public static final String WEIGHT=BASE_URL+"weight";

 //get immunization
 public static final String IMMUNIZATION=BASE_URL+"immunizationdNotGiven";

 //give immunization
 public static final String GIVE_IMMUNIZATION=BASE_URL+"giveimmunization";

 //give immunization
 public static final String RECORD_WEIGHT=BASE_URL+"recordWeight";

 //give immunization
 public static final String RECORD_HEIGHT=BASE_URL+"recordHeight";

 //get mother id
 public static final String GET_MOTHER_ID=BASE_URL+"getMotherId";

 //get last immunization given
 public static final String GET_LAST_IMMUNIZATION=BASE_URL+"getLastImmunizationGiven";

 //get list of all immunization given
 public static final String GET_LIST_OF_IMMUNIZATION=BASE_URL+"allImmunizationGiven";
    //send message
    public static final String SEND_MESSAGE=BASE_URL+"postForum";

    //get messages
    public static final String GET_MESSAGES=BASE_URL+"getMessages";

    //get next visit
    public static final String GET_NEXT_VISIT=BASE_URL+"nextVisit";

    //report
    public static final String MONTHLY_REPORT=BASE_URL+"report";
}
