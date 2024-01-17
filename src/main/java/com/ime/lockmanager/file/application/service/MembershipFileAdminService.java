package com.ime.lockmanager.file.application.service;

import com.ime.lockmanager.common.format.exception.file.InValidCheckingException;
import com.ime.lockmanager.common.format.exception.file.NotValidExcelFormatException;
import com.ime.lockmanager.common.format.exception.major.majordetail.NotFoundMajorDetailException;
import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.file.application.port.in.usecase.MembershipFileAdminUseCase;
import com.ime.lockmanager.major.application.port.out.MajorDetailQueryPort;
import com.ime.lockmanager.major.domain.MajorDetail;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.in.dto.UpdateUserDueInfoDto;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Transactional
@Service
@RequiredArgsConstructor
public class MembershipFileAdminService implements MembershipFileAdminUseCase {
    private final UserUseCase userUseCase;
    private final UserQueryPort userQueryPort;
    private final MajorDetailQueryPort majorDetailQueryPort;

    @Override
    public void ParseMembershipExcelForUpdateUserDuesInfo(MultipartFile membershipFile, Long userId) throws Exception {
        try (InputStream inputStream = membershipFile.getInputStream()) {
            Workbook workbook = getWorkbook(inputStream, membershipFile.getOriginalFilename());

            User user = getUserById(userId);
            MajorDetail majorDetail = getMajorDetailByMajorName(user.getMajorDetail().getMajor().getName());

            processExcelData(workbook, majorDetail, user);
        }
    }

    private Workbook getWorkbook(InputStream inputStream, String originalFilename) throws IOException {
        String extension = FilenameUtils.getExtension(originalFilename);
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new NotValidExcelFormatException();
        }

        return (extension.equals("xlsx")) ? new XSSFWorkbook(inputStream) : new HSSFWorkbook(inputStream);
    }

    private User getUserById(Long userId) {
        return userQueryPort.findByIdWithMajorDetailWithMajor(userId)
                .orElseThrow(NotFoundUserException::new);
    }

    private MajorDetail getMajorDetailByMajorName(String majorName) {
        return majorDetailQueryPort.findByNameWithMajor(majorName)
                .orElseThrow(NotFoundMajorDetailException::new);
    }

    private void processExcelData(Workbook workbook, MajorDetail majorDetail, User user) throws Exception {
        for (int sheet = 0; sheet < workbook.getNumberOfSheets(); sheet++) {
            Sheet workSheet = workbook.getSheetAt(sheet);
            for (int i = 1; i < workSheet.getPhysicalNumberOfRows(); i++) {
                Row row = workSheet.getRow(i);

                if (row != null && row.getCell(0) != null && row.getCell(1) != null && row.getCell(2) != null) {
                    row.getCell(0).setCellType(CellType.STRING);
                    row.getCell(1).setCellType(CellType.STRING);
                    row.getCell(2).setCellType(CellType.STRING);

                    String studentNum = row.getCell(0).getStringCellValue();
                    String studentName = row.getCell(1).getStringCellValue();
                    String checkDues = row.getCell(2).getStringCellValue();

                    //빈칸이 있는것은 에러를 뱉고 싶어서 조건을 위에다 씀
                    if(studentName.length()==0||studentNum.length()==0||checkDues.length()==0){
                        throw new InValidCheckingException();
                    }

                    if(studentName.length()==0&&studentNum.length()==0&&checkDues.length()==0){
                        continue;
                    }
                    boolean isDues = determineDuesStatus(checkDues);
                    userUseCase.updateUserDueInfoOrSave(UpdateUserDueInfoDto.builder()
                            .isDue(isDues)
                            .studentNum(studentNum)
                            .name(studentName)
                            .majorDetail(majorDetail)
                            .build());
                }
            }
        }
    }

    private boolean determineDuesStatus(String checkDues) {
        switch (checkDues.toLowerCase()) {
            case "o":
            case "0":
            case "O":
                return true;
            case "x":
            case "X":
                return false;
            default:
                throw new InValidCheckingException();
        }
    }

}
