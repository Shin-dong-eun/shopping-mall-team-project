package com.project.shop.controller;

import com.project.shop.dto.OrderDto;
import com.project.shop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 구매 버튼을 눌렀을 때 즉시 구매 처리, DB에 내용 저장하기 위한 컨트롤러.
    // 비동기 처리를 위해 REST api 사용하여 JSON으로 요청-응답
    @PostMapping("/order")
    @ResponseBody
    public ResponseEntity order(@RequestBody @Valid OrderDto orderDto,
                                BindingResult bindingResult, Principal principal) {
        // BindingResult 이용하여 넘어오는 주문 폼의 값이 검증에 실패했을 경우
        // 예외로 처리하여 ResponseEntity에 담아 응답
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrorList) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        // principal 객체에 담긴 현재 로그인 된 유저의 이름 가져오기(모름)
        String email = principal.getName();
        // 주문 번호를 담기 위한 Long형 변수 선언
        Long orderId;

        // 주문 번호와 유저 이메일로 새로운 주문을 생성하고 예외가 발생하면 오류,
        // 잘 처리됐다면 정상적으로 생성된 주문 번호 응답 객체로 반환
        try{
            orderId = orderService.order(orderDto, email);
        } catch(Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}
