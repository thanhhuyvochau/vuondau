package fpt.capstone.vuondau.config.notify;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;


@Configuration
@ConfigurationProperties(prefix = "config")
@PropertySource(value = "classpath:notification-message.yaml", factory = YamlPropertySourceFactory.class)
public class NotificationProperties {
    private List<NotifyDetail> notify = new ArrayList<>();

    public List<NotifyDetail> getNotify() {
        return notify;
    }

    public void setNotify(List<NotifyDetail> notify) {
        this.notify = notify;
    }

    public static class NotifyDetail {
        private String title;
        private String content;
        private String entity;
        private String code;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getEntity() {
            return entity;
        }

        public void setEntity(String entity) {
            this.entity = entity;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
