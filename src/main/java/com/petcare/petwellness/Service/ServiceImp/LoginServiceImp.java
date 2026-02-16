package com.petcare.petwellness.Service.ServiceImp;

import com.petcare.petwellness.DTO.Request.LoginRequestDto;
import com.petcare.petwellness.DTO.Request.SetNewPasswordRequestDto;
import com.petcare.petwellness.DTO.Response.LoginResponseDto;
import com.petcare.petwellness.Domain.Entity.User;
import com.petcare.petwellness.Enums.UserStatus;
import com.petcare.petwellness.Exceptions.CustomException.BadRequestException;
import com.petcare.petwellness.Exceptions.CustomException.ResourceNotFoundException;
import com.petcare.petwellness.Exceptions.CustomException.UnauthorizedException;
import com.petcare.petwellness.Repository.UserRepository;
import com.petcare.petwellness.Service.LoginService;
import com.petcare.petwellness.Util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImp implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginServiceImp(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
public LoginResponseDto login(LoginRequestDto request) {

    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new UnauthorizedException("Invalid credentials");
    }

    if (user.getStatus() == null || user.getStatus() == UserStatus.PENDING) {
        throw new BadRequestException("Admin approval pending");
    }
    if (user.getStatus() == UserStatus.REJECTED) {
        String reason = user.getRejectionReason() == null ? "No reason provided" : user.getRejectionReason();
        throw new BadRequestException("Application rejected: " + reason);
    }

    String token = jwtUtil.generateToken(
            user.getEmail(),
            user.getRole().name()
    );

    boolean changePasswordRequired = user.isFirstLogin();

    return new LoginResponseDto(token, changePasswordRequired);
}

@Override
public void setNewPassword(String email, SetNewPasswordRequestDto request) {

    if (email == null || email.isBlank()) {
        throw new UnauthorizedException("Invalid authenticated user context");
    }

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    if (!user.isFirstLogin()) {
        throw new BadRequestException("Password already set");
    }

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));

    user.setFirstLogin(false);

    userRepository.save(user);
}


}
