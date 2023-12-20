package com.study.labsystem.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabNameAndColloege {
    private List<LabTypeName> labTypeNameList;
    private List<LabCollegeName> labCollegeNameList;
}
