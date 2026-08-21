package com.example.system.service;

import com.example.auth.util.AuthContext;
import com.example.common.exception.BusinessException;
import com.example.crud.service.EntityCrudService;
import com.example.system.domain.dto.UserDto;
import com.example.system.domain.dto.UserPasswordDto;
import com.example.system.domain.dto.UserProfileDto;
import com.example.common.domain.entity.User;
import com.example.system.domain.enums.SystemError;
import com.example.system.domain.query.UserQueryCondition;
import com.example.system.domain.vo.UserVo;
import com.example.common.repository.RoleRepository;
import com.example.common.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService extends EntityCrudService<User, UserQueryCondition, UserVo, UserDto> {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthContext authContext;

    public UserVo getCurrentUserVo() {
        User user = authContext.getCurrentUser();
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
            throw new BusinessException(SystemError.USERNAME_EXISTED);
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
        User user = authContext.getCurrentUser();
        boolean matches = passwordEncoder.matches(dto.getSrcPassword(), user.getPassword());
        if (!matches) {
            throw new BusinessException(SystemError.SRC_PASSWORD_MISMATCH);
        }
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new BusinessException(SystemError.TWICE_INPUT_PASSWORD_MISMATCH);
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        repository.save(user);
    }

    public void updateProfile(UserProfileDto dto) {
        User user = authContext.getCurrentUser();
        checkUsernameConflict(dto.getUsername(), user.getId());
        user.setUsername(dto.getUsername());
        user.setNickname(dto.getNickname());
        repository.save(user);
    }
}
