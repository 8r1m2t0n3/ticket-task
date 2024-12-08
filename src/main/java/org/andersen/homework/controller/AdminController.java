package org.andersen.homework.controller;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dto.user.admin.AdminDto;
import org.andersen.homework.model.dto.user.admin.AdminSaveDto;
import org.andersen.homework.service.user.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

  private final AdminService adminService;

  @PostMapping
  public ResponseEntity<AdminDto> saveAdmin(@RequestBody AdminSaveDto adminSaveDto) {
    return new ResponseEntity<>(adminService.save(adminSaveDto), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAdminById(@PathVariable UUID id) {
    adminService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AdminDto> getAdminById(@PathVariable UUID id) {
    return new ResponseEntity<>(adminService.getById(id), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<AdminDto>> getAllAdmins() {
    return new ResponseEntity<>(adminService.getAll(), HttpStatus.OK);
  }
}
