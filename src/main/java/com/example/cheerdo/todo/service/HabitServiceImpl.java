package com.example.cheerdo.todo.service;

import com.example.cheerdo.entity.Calender;
import com.example.cheerdo.entity.Habit;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Todo;
import com.example.cheerdo.entity.enums.Type;
import com.example.cheerdo.repository.CalenderRepository;
import com.example.cheerdo.repository.HabitRepository;
import com.example.cheerdo.repository.MemberRepository;
import com.example.cheerdo.repository.TodoRepository;
import com.example.cheerdo.todo.dto.request.GetTodoRequestDto;
import com.example.cheerdo.todo.dto.request.WriteHabitRequestDto;
import com.example.cheerdo.todo.dto.response.HabitInfoResponseDto;
import com.example.cheerdo.todo.dto.response.HabitResponseDto;
import com.example.cheerdo.todo.dto.response.TodoResponseDto;
import javax.swing.text.html.Option;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class HabitServiceImpl implements HabitService {
    private final Logger logger = LoggerFactory.getLogger(HabitServiceImpl.class);

    private final MemberRepository memberRepository;

    private final HabitRepository habitRepository;

    private final CalenderRepository calenderRepository;

    private final TodoRepository todoRepository;

    @Override
    public Long writeHabit(WriteHabitRequestDto writeHabitRequestDto) {
        // habit에 저장
        Member member = memberRepository.findById(writeHabitRequestDto.getMemberId()).get();
        Habit habit = habitRepository.save(writeHabitRequestDto.requestTodHabit(member));

        // 저장 동시에 startDate부터 endDate까지 Calender Entity가 생긴다.
        for (LocalDate indexDate = writeHabitRequestDto.getStartDate();
             indexDate.isBefore(writeHabitRequestDto.getEndDate().plusDays(1));
             indexDate = indexDate.plusDays(1)) {
            LocalDate finalIndexDate = indexDate;
            Calender calender =
                    calenderRepository.findByMemberAndDate(member, finalIndexDate)
                            .orElseGet(() -> calenderRepository.save(
                                    Calender.builder()
                                            .member(member)
                                            .date(finalIndexDate)
                                            .build()
                            ));
            // calender에 habit을 todo로 변환해 넣어준다.
            Todo todo = Todo.builder()
                    .calender(calender)
                    .type(Type.HABIT)
                    .content(habit.getContent())
                    .isSuccess(false)
                    .habit(habit)
                    .todoId(UUID.randomUUID().toString())
                    .build();
            todoRepository.save(todo);
            calender.addTodo(todo);
            calender.updateRate();
        }
        return habit.getId();
    }

    @Override
    public void deleteHabit(Long habitId) {
        Habit habit = habitRepository.findById(habitId).get();
        Member member = habit.getMember();
        todoRepository.deleteAllByHabit(habit);

        for (LocalDate indexDate = habit.getStartDate();
             indexDate.isBefore(habit.getEndDate().plusDays(1));
             indexDate = indexDate.plusDays(1)) {
            Calender calender = calenderRepository.findByMemberAndDate(member, indexDate).get();
            Optional<Todo> todo = calender.getTodos().stream()
                    .filter(t -> t.getHabit() == habit)
                    .findFirst();
            if (todo.isPresent()) {
                calender.deleteTodo(todo.get());
            }
            calender.updateRate();
            if (calender.getTodos().size() == 0) {
                calenderRepository.delete(calender);
            }
        }
        habitRepository.delete(habit);
    }

    @Override
    public List<HabitResponseDto> getMyHabits(GetTodoRequestDto getTodoRequestDto) {
        Member member = memberRepository.findById(getTodoRequestDto.getMemberId()).get();
        Optional<Calender> calender = calenderRepository.findByMemberAndDate(member, getTodoRequestDto.getSearchDate());

        if (calender.isEmpty()) {
            return new ArrayList<>();
        }
        List<Todo> todos = todoRepository.findAllByCalenderAndType(calender.get()
                , Type.valueOf(getTodoRequestDto.getType())).get();

        return todos.stream()
                .map(todo -> todo.entityToHabitResponseDto())
                .collect(Collectors.toList());
    }

    @Override
    public List<HabitInfoResponseDto> getHabitInfo(String memberId) {
        return habitRepository.findAllByMember(memberRepository.findById(memberId).get())
                .get()
                .stream()
                .map(habit -> habit.entityToHabitInfoDto())
                .collect(Collectors.toList());
    }
}
