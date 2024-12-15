package org.andersen.homework.controller;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dto.user.UserDto;
import org.andersen.homework.service.user.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

  private final AdminService adminService;

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto saveAdmin() {
    return adminService.save();
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAdminById(@PathVariable UUID id) {
    adminService.deleteById(id);
  }

  @GetMapping("/{id}")
  public UserDto getAdminById(@PathVariable UUID id) {
    return adminService.getById(id);
  }

  @GetMapping
  public List<UserDto> getAllAdmins() {
    return adminService.getAll();
  }
}
