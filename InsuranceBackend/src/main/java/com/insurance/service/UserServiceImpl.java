package com.insurance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insurance.apiResponse.ApiResponse;
import com.insurance.dto.LoginDto;
import com.insurance.dto.ResetPasswordDto;
import com.insurance.entities.User;
import com.insurance.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public ApiResponse signUpUser(User user) {
		long reg_userId = userRepository.signUpUser(user);

		if (reg_userId != 0) {

			ApiResponse apiResponse = new ApiResponse(200, "registered Successfully", user);
			return apiResponse;
		} else {
			ApiResponse apiResponse = new ApiResponse(400, "registration Failed ", user);
			return apiResponse;
		}

	}

	@Override
	public ApiResponse login(LoginDto loginDto) {

		User user = userRepository.findByEmail(loginDto.getUserEmail());
		if (user != null) {
			if (user.getUserPassword().equals(loginDto.getUserPassword())) {

				return new ApiResponse(200, "SUCCESS", user);
			}

		}

		return new ApiResponse(400, "FAILED", null);

	}

	@Override
	public ApiResponse viewAllUsers() {
		long users = userRepository.viewAllUsers();
		if (users != 0) {
			return new ApiResponse(200, "SUCCESS", users);
		}
		return new ApiResponse(400, "FAILED", null);
	}

	@Override
	public ApiResponse findUserById(long userId) {
		User user = userRepository.findUserById(userId);
		if (user != null)
			return new ApiResponse(200, "SUCCESS", user);
		else
			return new ApiResponse(400, "FAILED", null);
	}

	@Override
	public ApiResponse deleteUser(long userId) {
		User user = userRepository.findUserById(userId);
		if (user != null)
		{
			userRepository.DeleteUser(userId);
			return new ApiResponse(200, "SUCCESS", user);
		}
		else
		{
			return new ApiResponse(400, "FAILED", null);
		}
	}

	public ApiResponse updatePassword(String email, String newPassword) {

		User user = userRepository.findByEmail(email);
		user.setUserPassword(newPassword);
		userRepository.save(user);
		return new ApiResponse(200, "UPDATED", user);
	}

	@Override
	public ApiResponse findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			return new ApiResponse(200, "User Fetched by email", user);
		}
		return new ApiResponse(404, "No user found", null);
	}

	public ApiResponse updatePassword(ResetPasswordDto resetPasswordDto) {

		User user = userRepository.findByEmail(resetPasswordDto.getUserEmail());
		user.setUserPassword(resetPasswordDto.getUserPassword());
		userRepository.save(user);
		return new ApiResponse(200, "UPDATED", user);
	}

}
