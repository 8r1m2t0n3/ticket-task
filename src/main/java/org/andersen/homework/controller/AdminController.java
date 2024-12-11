package org.andersen.homework.controller;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dto.user.admin.AdminDto;
import org.andersen.homework.model.dto.user.admin.AdminSaveDto;
import org.andersen.homework.service.user.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public AdminDto saveAdmin(@RequestBody AdminSaveDto adminSaveDto) {
    return adminService.save(adminSaveDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAdminById(@PathVariable UUID id) {
    adminService.deleteById(id);
  }

  @GetMapping("/{id}")
  public AdminDto getAdminById(@PathVariable UUID id) {
    return adminService.getById(id);
  }

  @GetMapping
  public List<AdminDto> getAllAdmins() {
    return adminService.getAll();
  }
}
