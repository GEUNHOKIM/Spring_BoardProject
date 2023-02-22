package com.project.project_board.service;

import com.project.project_board.dto.LoginRequestDto;
import com.project.project_board.dto.MessageResponseDto;
import com.project.project_board.dto.SignupRequestDto;
import com.project.project_board.entity.JwtUtil;
import com.project.project_board.entity.User;
import com.project.project_board.entity.UserRoleEnum;
import com.project.project_board.entity.enumset.ErrorType;
import com.project.project_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static com.project.project_board.exception.UserExceptionHandling.responseException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<Object> signup(SignupRequestDto signupRequestDto) {

//        if (bindingResult.hasErrors()) {
//            return responseException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
//        }


        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        Optional<User> found = userRepository.findByUsername(username);

        if (found.isPresent()) {
            return responseException(ErrorType.DUPLICATED_USERNAME);
        }

        UserRoleEnum role = UserRoleEnum.USER;

        if (signupRequestDto.getAdmin()) {

            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);

        userRepository.save(user);

        return ResponseEntity.ok(MessageResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .msg("회원가입 성공")
                .build());
    }


    @Transactional(readOnly = true)
    public ResponseEntity login(LoginRequestDto loginRequestDto) {

        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty() || !( user.get().getPassword().equals(password)) ) {
            return responseException(ErrorType.NOT_MATCHING_INFO);
        }

        HttpHeaders header = new HttpHeaders();
        header.set(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.get().getUsername(), user.get().getRole()));

        return ResponseEntity.ok()
                .headers(header)
                .body(MessageResponseDto.builder()
                    .statusCode(HttpStatus.OK.value())
                    .msg("로그인 성공")
                    .build());
    }

}
