package com.example.appenglishlanguagelearning.service;

import com.example.appenglishlanguagelearning.entity.Dictionary;
import com.example.appenglishlanguagelearning.repository.DictionaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DictionaryService {


    private final DictionaryRepository dictionaryRepository;

    public void importVerbsFromExcel(MultipartFile file) throws IOException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            String word = row.getCell(1).getStringCellValue();
            String translation = row.getCell(2).getStringCellValue();

            if(!dictionaryRepository.existsByWordIgnoreCase(word)){
                Dictionary dictionary = new Dictionary();
                dictionary.setUser(null);
                dictionary.setWord(word);
                dictionary.setTranslateWord(translation);
                dictionaryRepository.save(dictionary);
            }
        }

        workbook.close();
    }
}
