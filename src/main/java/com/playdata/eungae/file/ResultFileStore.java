package com.playdata.eungae.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultFileStore {
    private String folderPath;
    private String storeFileName;
    private String originalFileName;
}
