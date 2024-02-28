package com.ime.lockmanager.locker.application.service;

import com.ime.lockmanager.common.format.exception.locker.NotFoundLockerException;
import com.ime.lockmanager.common.format.exception.major.majordetail.NotFoundMajorDetailException;
import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.file.application.service.ImageFileAdminService;
import com.ime.lockmanager.locker.adapter.in.res.LockersInfoInMajorResponse;
import com.ime.lockmanager.locker.adapter.in.res.dto.LockersInfoDto;
import com.ime.lockmanager.locker.adapter.in.res.dto.LockersInfoInMajorDto;
import com.ime.lockmanager.locker.application.port.in.LockerDetailUseCase;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import com.ime.lockmanager.locker.application.port.in.dto.CreatedLockerInfo;
import com.ime.lockmanager.locker.application.port.in.req.FindAllLockerInMajorRequestDto;
import com.ime.lockmanager.locker.application.port.in.req.LockerCreateRequestDto;
import com.ime.lockmanager.locker.application.port.in.req.ModifyLockerInfoReqeustDto;
import com.ime.lockmanager.locker.application.port.in.res.LeftLockerResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerCreateResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerCommandPort;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.Period;
import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.locker.domain.lockerdetail.dto.LockerDetailCreateDto;
import com.ime.lockmanager.locker.domain.lockerdetail.dto.LockerDetailInfo;
import com.ime.lockmanager.major.application.port.out.major.MajorQueryPort;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.ime.lockmanager.locker.adapter.in.req.NumberIncreaseDirection.DOWN;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
class LockerService implements LockerUseCase {
    private final LockerQueryPort lockerQueryPort;
    private final LockerDetailUseCase lockerDetailUseCase;
    private final MajorQueryPort majorQueryPort;
    private final UserQueryPort userQueryPort;
    private final ImageFileAdminService imageFileAdminService;
    private final LockerCommandPort lockerCommandPort;

    //남은 사물함 목록
    @Override
    public LeftLockerResponseDto getCreatedLockers(Long majorId) {
        List<CreatedLockerInfo> createdLockerInfos = lockerQueryPort.findByMajorId(majorId).stream()
                .map(locker -> CreatedLockerInfo.builder()
                        .id(locker.getId())
                        .startReservationTime(locker.getPeriod().getStartDateTime())
                        .endReservationTime(locker.getPeriod().getEndDateTime())
                        .totalColumn(locker.getTotalColumn())
                        .totalRow(locker.getTotalRow())
                        .permitStates(locker.getPermitUserState())
                        .image(locker.getImageUrl())
                        .name(locker.getName())
                        .permitTiers(locker.getPermitUserTier())
                        .build())
                .collect(Collectors.toList());
        return LeftLockerResponseDto.builder()
                .createdLockerInfo(createdLockerInfos)
                .build();
    }


    @Override
    public void modifyLockerInfo(ModifyLockerInfoReqeustDto reqeustDto) throws IOException {
        Locker locker = lockerQueryPort.findByLockerId(reqeustDto.getLockerId()).orElseThrow(NotFoundLockerException::new);
        if (reqeustDto.getImage() != null) {
            String originalImageUrl = locker.getImageUrl();
            if (!originalImageUrl.isBlank()) {
                //기존 이미지 삭제
                imageFileAdminService.deleteImageToS3(originalImageUrl);
            }
            String newImageUrl = imageFileAdminService.saveImageToS3(reqeustDto.getImage());
            locker.modifiedImageInfo(newImageUrl);
        }

        if (reqeustDto.getStartTime() != null && reqeustDto.getEndTime() != null) {
            locker.modifiedDateTime(Period.builder()
                    .startDateTime(reqeustDto.getStartTime())
                    .endDateTime(reqeustDto.getEndTime())
                    .build());
        }
        if (!reqeustDto.getUserStates().isEmpty() || reqeustDto.getUserStates() != null) {
            locker.getPermitUserState().clear();
            reqeustDto.getUserStates().stream().forEach(userState -> locker.getPermitUserState().add(userState));
        }
        if (!reqeustDto.getUserTiers().isEmpty() || reqeustDto.getUserTiers() != null) {
            locker.getPermitUserTier().clear();
            reqeustDto.getUserTiers().stream().forEach(userTier -> locker.getPermitUserTier().add(userTier));
        }
        if (reqeustDto.getLockerName() != null) {
            locker.rename(reqeustDto.getLockerName());
        }
    }

    @Override
    public LockersInfoInMajorResponse findAllLockerInMajor(FindAllLockerInMajorRequestDto requestDto) {
        User user = userQueryPort.findByIdWithMajorDetailAndMajor(requestDto.getUserId())
                .orElseThrow(NotFoundUserException::new);
//        userUseCase.findByStudentNum(requestDto.getStudentNum());
        Major major = user.getMajorDetail().getMajor();
        List<Locker> lockerByUserMajor = lockerQueryPort.findLockerByUserMajor(major);
        return LockersInfoInMajorResponse.builder().lockersInfo(lockerByUserMajor.stream()
                        .map(locker -> LockersInfoDto.builder()
                                .lockerDetail(getLockerDetailInfos(locker))
                                .locker(getLockersInfoInMajorDto(locker))
                                .build()
                        ).collect(Collectors.toList()))
                .build();
    }

    private List<LockerDetailInfo> getLockerDetailInfos(Locker locker) {
        return lockerDetailUseCase.findLockerDetailByLocker(locker).stream()
                .map(
                        lockerDetail ->
                                LockerDetailInfo.builder()
                                        .lockerNum(lockerDetail.getLockerNum())
                                        .status(lockerDetail.getLockerDetailStatus())
                                        .columnNum(lockerDetail.getColumn_num())
                                        .rowNum(lockerDetail.getRow_num())
                                        .id(lockerDetail.getId())
                                        .build()
                ).collect(Collectors.toList());
    }

    private static LockersInfoInMajorDto getLockersInfoInMajorDto(Locker locker) {
        return LockersInfoInMajorDto.builder()
                .id(locker.getId())
                .endReservationTime(locker.getPeriod().getEndDateTime())
                .startReservationTime(locker.getPeriod().getStartDateTime())
                .name(locker.getName())
                .totalColumn(locker.getTotalColumn())
                .totalRow(locker.getTotalRow())
                .permitTiers(locker.getPermitUserTier())
                .permitStates(locker.getPermitUserState())
                .image(locker.getImageUrl())
                .build();
    }

    @Override
    public LockerCreateResponseDto createLocker(LockerCreateRequestDto requestDto, Long majorId) throws IOException {
        /*String imageUrl = null;
        if (!requestDto.getImage().isEmpty()) { //이미지가 있을때
            imageUrl = imageFileAdminService.saveImageToS3(requestDto.getImage());
        }*/
        Major userMajor = majorQueryPort.findById(majorId)
                .orElseThrow(NotFoundMajorDetailException::new);//에러 새로 만들어야함
        Locker createdLocker = Locker.createLocker(
                requestDto.toLockerCreateDto(
                        userMajor/*, imageUrl*/
                )
        );
        Locker saveLocker = lockerCommandPort.save(createdLocker);
        createLockerDetail(requestDto, saveLocker);
        return LockerCreateResponseDto.builder()
                .createdLockerId(saveLocker.getId())
                .createdLockerName(saveLocker.getName())
                .build();

    }

    private void createLockerDetail(LockerCreateRequestDto lockerCreateRequestDto, Locker saveLocker) {
        int num = 0;
        String column = lockerCreateRequestDto.getTotalColumn();
        String row = lockerCreateRequestDto.getTotalRow();

        int totalColumns = Integer.valueOf(column);
        int totalRows = Integer.valueOf(row);

        if (lockerCreateRequestDto.getNumberIncreaseDirection().equals(DOWN)) {
            for (int i = 1; i <= totalColumns; i++) {
                for (int j = 1; j <= totalRows; j++) {
                    lockerDetailUseCase.saveLockerDetail(
                            LockerDetailCreateDto.builder()
                                    .lockerNum(Integer.toString(++num))
                                    .rowNum(Integer.toString(j))
                                    .columnNum(Integer.toString(i))
                                    .locker(saveLocker)
                                    .build()
                    );
                }
            }
        } else {
            for (int i = 1; i <= totalRows; i++) {
                for (int j = 1; j <= totalColumns; j++) {
                    lockerDetailUseCase.saveLockerDetail(
                            LockerDetailCreateDto.builder()
                                    .lockerNum(Integer.toString(++num))
                                    .rowNum(Integer.toString(i))
                                    .columnNum(Integer.toString(j))
                                    .locker(saveLocker)
                                    .build()
                    );
                }
            }
        }
    }
}
