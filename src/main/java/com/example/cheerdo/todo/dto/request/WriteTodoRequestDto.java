package com.example.cheerdo.todo.dto.request;

import com.example.cheerdo.todo.enums.Type;
import lombok.*;

@Getter
@NoArgsConstructor
public class WriteTodoRequestDto {
    private String userId;

    private String type;
    // time 프론트에서 받을건지 서버에서 해결할 것인지?
    // private LocalDateTime writeTime;
    // private LocalDateTime date;
    private String todo;
}
