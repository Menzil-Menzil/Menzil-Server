package seoultech.capstone.menjil.domain.follow.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seoultech.capstone.menjil.domain.follow.application.FollowService;
import seoultech.capstone.menjil.domain.follow.dto.request.FollowRequest;
import seoultech.capstone.menjil.global.common.dto.ApiResponse;
import seoultech.capstone.menjil.global.exception.CustomException;
import seoultech.capstone.menjil.global.exception.ErrorCode;
import seoultech.capstone.menjil.global.exception.SuccessCode;

import javax.validation.Valid;

import static seoultech.capstone.menjil.global.common.dto.ApiResponse.success;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/request")
    public ResponseEntity<ApiResponse<?>> followRequest(@Valid @RequestBody FollowRequest followRequest) {
        int FOLLOW_CREATED = 0;
        int FOLLOW_DELETED = 1;
        int INTERNAL_SERVER_ERROR = -100;

        int result = followService.followRequest(followRequest);
        if (result == FOLLOW_CREATED) {
            return ResponseEntity.status(HttpStatus.CREATED).body(success(SuccessCode.FOLLOW_CREATED));
        } else if (result == FOLLOW_DELETED) {
            return ResponseEntity.status(HttpStatus.CREATED).body(success(SuccessCode.FOLLOW_DELETED));
        } else {
            throw new CustomException(ErrorCode.SERVER_ERROR);
        }
    }

    @GetMapping("/check-status")
    public ResponseEntity<ApiResponse<Boolean>> checkFollowStatus(@RequestParam("userNickname") String userNickname,
                                                                  @RequestParam("followNickname") String followNickname) {
        boolean FOLLOW_EXISTS = true;
        boolean FOLLOW_NOT_EXISTS = false;

        boolean result = followService.checkFollowStatus(userNickname, followNickname);
        if (result == FOLLOW_EXISTS) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(success(SuccessCode.FOLLOW_CHECK_SUCCESS, FOLLOW_EXISTS));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(success(SuccessCode.FOLLOW_CHECK_SUCCESS, FOLLOW_NOT_EXISTS));
        }
    }
}