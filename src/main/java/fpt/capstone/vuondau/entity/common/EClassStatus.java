package fpt.capstone.vuondau.entity.common;

public enum EClassStatus {
    RECRUITING, // Lớp đang tuyển gia sư
    PENDING, // Lớp đã tới hạn đóng tuyển gia sư đang đợi quản lý xử lý
    REQUESTING, // Lớp được yêu cầu mở nhưng chưa sẵn sàng duyệt của giáo viên
    WAITING, // Lớp đã sẵn được được duyệt
    REJECTED, // Từ chối mở lớp
    NOTSTART, // Lớp được duyệt và đang trong quá trình tuyển sinh

    STARTING, // Lớp ngưng tuyển sinh và đang bắt đầu dạy

    ENDED, // Lớp đã kết thúc

    CANCEL, // Lớp bị hủy

    RESPONSING, // Lớp tuyển giáo viên đang đợi phản hồi từ giáo viên
    All,


}
