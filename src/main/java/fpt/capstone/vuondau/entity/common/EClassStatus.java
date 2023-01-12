package fpt.capstone.vuondau.entity.common;

public enum EClassStatus {
    RECRUITING, // Lớp đang tuyển gia sư
    REQUESTING, // Lớp được yêu cầu mở nhưng chưa sẵn sàng duyệt của giáo viên
    WAITING, // Lớp đã sẵn được được duyệt
    NOTSTART, // Lớp được duyệt và đang trong quá trình tuyển sinh

    STARTING, // Lớp ngưng tuyển sinh và đang bắt đầu dạy

    ENDED, // Lớp đã kết thúc
    All ,

    REJECTED

}
