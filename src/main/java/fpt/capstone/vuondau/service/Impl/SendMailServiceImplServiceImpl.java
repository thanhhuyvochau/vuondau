package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.service.IPaymentService;
import fpt.capstone.vuondau.service.ISendMailService;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Service
@Transactional
public class SendMailServiceImplServiceImpl implements ISendMailService {

    @Override
    public String sendMail() {

        String to = "nguyenlehaidang.290698@gmail.com";
        String host = "smtp.gmail.com";
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("vuondaueducation@gmail.com", "vefmctjjernbycmt");
            }

        });
        //session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
//            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            Multipart multipart = new MimeMultipart();

            MimeBodyPart attachmentPart = new MimeBodyPart();

            MimeBodyPart textPart = new MimeBodyPart();

            try {
                File f =new File("C:\\Users\\nguye\\Downloads\\Alepay_checkout_api_v3_vi.pdf");



                attachmentPart.attachFile(f);
                textPart.setText(" \"Qua buổi trao đổi, chúng tôi đánh giá tốt kiến thức chuyên môn cũng như khả năng làm việc của Bạn. Chúng tôi nhận thấy nếu được tạo điều kiện tốt, Bạn sẽ có những đóng góp to lớn vào sự phát triển và vững mạnh của công ty, đồng thời Vườn Đậu Education cũng là môi trường phù hợp để Bạn có thể phát triển tốt nhất khả năng của mình.\" +\n" +
                        "                    \"\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"* Thông tin tài khoản của bạn : \" +\n" +
                        "                    \"Tài khoản : \" +\n" +
                        "                    \"Mật Khẩu : \" +\n" +
                        "                    \"Vui lòng phản hồi Thư mời nhận việc cho công ty chúng tôi trong vòng 01 ngày làm việc kể từ khi nhận được email này. \" +\n" +
                        "                    \"Một lần nữa, Chúng tôi hân hoan chào đón Bạn gia nhập, làm việc tại công ty và tin tưởng sự hợp tác lâu dài.\");");
                multipart.addBodyPart(textPart);
                multipart.addBodyPart(attachmentPart);

            } catch (IOException e) {

                e.printStackTrace();

            }

            message.setContent(multipart);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }


        return to;
    }
}
