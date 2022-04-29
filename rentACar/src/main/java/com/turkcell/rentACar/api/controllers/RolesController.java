package com.turkcell.rentACar.api.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.turkcell.rentACar.business.abstracts.RoleService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.entities.concretes.Role;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolesController {
	private final RoleService roleService;

	@PostMapping("/saveRole")
	public ResponseEntity<DataResult<Role>> saveRole(@RequestBody @Valid Role role) {
		URI uri = URI
				.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/roles/saveRole").toUriString());
		return ResponseEntity.created(uri).body(roleService.saveRole(role));
	}

	@GetMapping("/getRoleById/{id}")
	public DataResult<Role> getRoleById(@RequestParam int id) {
		return roleService.getRoleById(id);
	}
}
