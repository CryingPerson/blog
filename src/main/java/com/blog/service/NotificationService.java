package com.blog.service;

import com.blog.repository.EmitterRepository;
import com.blog.repository.UserRepository;
import com.blog.response.NotificationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final UserRepository userRepository;
    private final EmitterRepository emitterRepository;

    private static final Long DEFAULT_TIMEOUT = 600L * 1000 * 60;

    public SseEmitter subscribe(Long memberId){

        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        emitterRepository.save(memberId, sseEmitter);

        Map<String, Object> testContent = new HashMap<>();
        testContent.put("content", "connected!");
        sendToClient(sseEmitter, memberId, testContent);

        SseEmitter.SseEventBuilder data = SseEmitter.event().id(String.valueOf(memberId)).data(testContent);



        sseEmitter.onCompletion( () -> {        // 정상 종료시
            emitterRepository.deleteById(memberId);
        });

        sseEmitter.onTimeout( () ->{            // 시간이 끝나서 종료시
            sseEmitter.complete();
            emitterRepository.deleteById(memberId);
        });

        return sseEmitter;

    }

    private void sendToClient(SseEmitter emitter, Long sseId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(String.valueOf(sseId))
                    .data(data));
            log.info("sendToClient sseId: {} data: {}", sseId, data);
        } catch (IOException exception) {
            log.error("Error sending data to client: {}", exception.getMessage());
            emitterRepository.deleteById(sseId);
            emitter.completeWithError(exception);
            throw new RuntimeException("이상함..");
        }
    }

    public void notify(Long memberId, NotificationDTO notificationDTO) {
        SseEmitter emitter = emitterRepository.get(memberId);
        if (emitter != null) {
            sendToClient(emitter, memberId, notificationDTO);
        } else {
            log.warn("No active SSE connection for memberId: {}", memberId);
        }
    }
}
