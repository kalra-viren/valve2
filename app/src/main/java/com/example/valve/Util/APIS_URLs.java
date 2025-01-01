package com.example.valve.Util;


public class APIS_URLs {
    public static String live_url = "https://mgltest.mahanagargas.com/AT/api/";

    public static String uat_url = "http://10.0.2.2:5089/api/";

    public static String base_url = live_url;

    public static String area_url = base_url + "Validation/CheckSTCNo/";

    public static String permit_url = "https://mgltest.mahanagargas.com/ApprovalTicketsWeb/CSEPermit.aspx?id=";
    public static String fetch_tickets_url = base_url + "STC/";
    public static String updateTicketStatus_url = base_url + "StatusClose/statusClose";
    public static String updateTicketStatus_url_approved = base_url + "StatusClose/statusApproved";

//    public static String uploadBitmap_url = base_url + "ClosedImages/upload"; commented on 26-12-2024 on 15.08
    public static String uploadBitmap_url = base_url + "PermitImage/CloseImg";

    public static String sendDataToApi_url = base_url + "ClosedPathsUploading/update";
    public static String fetchImagePaths_url = base_url + "UploadingPaths/GetImagePaths?permitId=";
    public static String loadImages_url = base_url + "ImageUpload/GetImageReal?filePath=";
    public static String fetchTickets_url = base_url + "GetRecordsByEmpId/";
    public static String validateCredentials_url = base_url + "Validation/ValidateCredentials";
    public static String validateCredentials_url_tpe = base_url + "Validation/validate-credentials";
    public static String fetchDistricts_url = base_url + "Districts/distinct-districts";
    public static String fetchValveChambers_url = base_url + "Districts/valve-chambers/";
    public static String fetchNumbersForName_url = base_url + "Districts/valve-chamber-id/";
    public static String fetchDICNames_url = base_url + "Districts/DIC-names";
    public static String CheckSTCNo_url = base_url + "Validation/CheckSTCNo/";
    public static String fetchQuestions_url = base_url + "permit/questionsWithHeadings";
    public static String nameToEid_url = base_url + "Validation/";
    public static String dataEntry_url = base_url + "DataEntry";
    //    public static String uploadImage_url = base_url + "ImageUpload/upload"; commented 26-12-2024 13.12
    public static String uploadImage_url = base_url + "PermitImage/SavePermitImg";

    public static String uploadPath_url = base_url + "UploadingPaths/update";
    public static String ReviewTickets_url = base_url + "ReviewTickets/";
    public static String ReviewMessage_url = base_url + "StatusClose/revieMessage";
    public static String ImageUplaod_url = base_url + "ImageUpload/GetImagesBatch";
    public static String closed_tickets_url = base_url + "ClosedSTC/";
    public static String reject_ref_msg_url = base_url + "ClosedSTC/reject-msg/";
}
