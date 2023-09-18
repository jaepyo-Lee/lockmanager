package com.ime.lockmanager.file.application.service;

import com.ime.lockmanager.common.format.exception.file.NotValidDuesCheckingException;
import com.ime.lockmanager.common.format.exception.file.NotValidExcelFormatException;
import com.ime.lockmanager.file.application.port.in.usecase.FileUseCase;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.in.dto.UpdateUserDueInfoDto;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService implements FileUseCase {
    private final UserUseCase userUseCase;
    private final UserQueryPort userQueryPort;

    @Override
    public void ParseMembershipExcelForUpdateUserDuesInfo(MultipartFile membershipFile) throws Exception {
        Workbook workbook = null;
        String extension = FilenameUtils.getExtension(membershipFile.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new NotValidExcelFormatException();
        }
        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(membershipFile.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(membershipFile.getInputStream());
        }
        for(int sheet=0;sheet<workbook.getNumberOfSheets();sheet++){
            Sheet workSheet = workbook.getSheetAt(sheet);
            System.out.println(workSheet.getPhysicalNumberOfRows());
            for (int i = 1; i < workSheet.getPhysicalNumberOfRows(); i++) {
                Row row = workSheet.getRow(i);
                row.getCell(0).setCellType(CellType.STRING);
                row.getCell(1).setCellType(CellType.STRING);
                row.getCell(2).setCellType(CellType.STRING);
                String studentNum = row.getCell(0).getStringCellValue();// 학번
                String studentName = row.getCell(1).getStringCellValue();// 학생이름
                String checkDues = row.getCell(2).getStringCellValue();// 학생회비 납부 여주
                boolean isDues;
                if (checkDues.equals("O") || checkDues.equals("o") || checkDues.equals("0")) {
                    isDues = true;
                } else if (checkDues.equals("X") || checkDues.equals("x") || checkDues.isBlank()) {
                    isDues = false;
                } else {
                    throw new NotValidDuesCheckingException();
                }
                System.out.println(studentNum);
                userUseCase.updateUserDueInfoOrSave(UpdateUserDueInfoDto.builder()
                        .isDue(isDues)
                        .studentNum(studentNum)
                        .name(studentName)
                        .build());

            }
        }
        workbook.close();
    }
}
