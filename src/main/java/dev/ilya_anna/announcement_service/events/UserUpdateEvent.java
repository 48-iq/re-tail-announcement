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
public class UserUpdateEvent {
    private String id;
    private String nickname;

    static UserUpdateEvent fromMap(Map<String, Object> map) {
        return UserUpdateEvent.builder()
            .id((String) map.get("id"))
            .nickname((String) map.get("nickname"))
        .build();
    }

    static Map<String, Object> toMap(UserUpdateEvent upe) {
        return Map.of(
            "id", upe.getId(),
            "nickname", upe.getNickname()
        );
    }
}
