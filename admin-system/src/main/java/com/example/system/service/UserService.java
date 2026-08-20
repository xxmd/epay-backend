package com.example.system.service;

import com.example.common.exception.BusinessException;
import com.example.crud.service.EntityCrudService;
import com.example.system.model.dto.UserDto;
import com.example.system.model.dto.UserPasswordDto;
import com.example.system.model.dto.UserProfileDto;
import com.example.system.model.entity.User;
import com.example.system.model.query.UserQueryCondition;
import com.example.system.model.vo.UserVo;
import com.example.system.repository.RoleRepository;
import com.example.system.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService extends EntityCrudService<User, UserQueryCondition, UserVo, UserDto> {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserVo getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            log.error("authentication is null in getCurrentUser method");
            return null;
        }
        User user = (User) authentication.getPrincipal();
        return mapper.toVo(user);
    }

    @Override
    public User dtoToEntityOnCreate(UserDto dto) {
        User user = super.dtoToEntityOnCreate(dto);
        user.setPassword("123456");
        return user;
    }

    @Override
    public void update(UserDto dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Id can't be null when update");
        }
        checkUsernameConflict(dto.getUsername(), dto.getId());
        super.update(dto);
    }

    private void checkUsernameConflict(String username, Long id) {
        Optional<User> optionalUser = repository.findByUsername(username);
        if (optionalUser.isPresent() && !optionalUser.get().getId().equals(id)) {
            String message = String.format(Locale.US, "用户名: %s已存在", username);
            throw new BusinessException(message);
        }
    }

    @Override
    public User dtoToEntity(UserDto dto) {
        User user = super.dtoToEntity(dto);
        user.setRoleSet(new HashSet<>(roleRepository.findAllById(dto.getRoleIdSet())));
        return user;
    }

    @Override
    public User dtoToEntityOnUpdate(UserDto userDto) {
        User user = super.dtoToEntityOnUpdate(userDto);
        Optional<User> optionalUser = repository.findById(userDto.getId());
        optionalUser.ifPresent(value -> user.setPassword(value.getPassword()));
        return user;
    }

    public UserVo findByUsername(String username) {
        Optional<User> optionalUser = repository.findByUsername(username);
        return optionalUser.map(user -> mapper.toVo(user)).orElse(null);
    }

    public UserVo findByEmail(String email) {
        Optional<User> optionalUser = repository.findByEmail(email);
        return optionalUser.map(user -> mapper.toVo(user)).orElse(null);
    }

    public void updatePassword(UserPasswordDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BusinessException("当前用户不存在");
        }
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            throw new BusinessException("当前用户不存在");
        }
        boolean matches = passwordEncoder.matches(dto.getSrcPassword(), user.getPassword());
        if (!matches) {
            throw new BusinessException("原密码错误");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new BusinessException("新密码和确认密码不一致");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        repository.save(user);
    }

    public void updateProfile(UserProfileDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BusinessException("当前用户不存在");
        }
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            throw new BusinessException("当前用户不存在");
        }
        checkUsernameConflict(dto.getUsername(), user.getId());
        user.setUsername(dto.getUsername());
        user.setNickname(dto.getNickname());
        repository.save(user);
    }
}
