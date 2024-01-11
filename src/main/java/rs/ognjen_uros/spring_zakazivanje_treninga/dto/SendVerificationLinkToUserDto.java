package rs.ognjen_uros.spring_zakazivanje_treninga.dto;


public class SendVerificationLinkToUserDto {
    private Long userId;
    private String link;

    public SendVerificationLinkToUserDto(Long userId, String link) {
        this.userId = userId;
        this.link = link;
    }

    public Long getUserId() {
        return userId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public SendVerificationLinkToUserDto(Long userId) {
        this.userId = userId;
    }
}

