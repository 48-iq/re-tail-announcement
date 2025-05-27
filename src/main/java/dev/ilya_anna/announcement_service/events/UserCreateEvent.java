package dev.ilya_anna.announcement_service.events;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateEvent {
    private String id;
    private String nickname;

    static UserCreateEvent fromMap(Map<String, Object> map) {
        return UserCreateEvent.builder()
            .id((String) map.get("id"))
            .nickname((String) map.get("nickname"))
        .build();
    }

    static Map<String, Object> toMap(UserCreateEvent uce) {
        return Map.of(
            "id", uce.getId(),
            "nickname", uce.getNickname()
        );
    }
}
