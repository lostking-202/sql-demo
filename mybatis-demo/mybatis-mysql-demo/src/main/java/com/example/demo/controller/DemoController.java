package com.example.demo.controller;

import com.example.demo.mapper.SysDeptMapper;
import com.example.demo.mapper.SysDictDataMapper;
import com.example.demo.mapper.SysUserMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.math3.util.Pair;

@RestController
public class DemoController {
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysDeptMapper sysDeptMapper;
    @Autowired
    SysDictDataMapper sysDictDataMapper;
    @GetMapping("/instance")
    public void getInstanceTest(){
        System.out.println(SingleInstance.getInstance());
    }
    @GetMapping("/start")
    public void startSql() throws IOException {
        ObjectMapper om=new ObjectMapper();
        var workBook=new XSSFWorkbook(Files.newInputStream(Paths.get("C:\\N-21AJPF3SS51N-Data\\j32zhang\\Downloads\\BusinessService_Application Mapping_1124.xlsx")));
        var sheet=workBook.getSheet("service catalogue");

        //var row=sheet.getRow(1);
    /*for(int j=1;j<10;j++){
      System.out.println(row.getCell(j).getStringCellValue());
    }*/
        var tier1Map=new LinkedHashMap<String,Integer>();
        var tier2Map=new LinkedHashMap<String,Integer>();
        var tier3Map=new LinkedHashMap<String,Integer>();

        {
            var ss=new LinkedHashSet<String>();
            for(int i=1;i<148;i++){
                var row=sheet.getRow(i);
                ss.add(row.getCell(2).getStringCellValue());
            }
            var sqlTemple="insert into business_service(id,tier1)values({0},''{1}'');";

            var sss=new LinkedList<>(ss);
            for(int i=0;i<sss.size();i++){
                tier1Map.put(sss.get(i),i+1);
                //Files.writeString(Path.of("C:\\Users\\j32zhang\\workspace\\java\\starter\\output\\tier1.sql"),MessageFormat.format(sqlTemple,i+1,sss.get(i))+System.lineSeparator(), StandardOpenOption.APPEND);
            }
        }

        {
            var tier2=new LinkedHashSet<Pair<String,String>>();
            for(int i=1;i<148;i++){
                var row=sheet.getRow(i);
                tier2.add(new Pair<>(row.getCell(2).getStringCellValue(),row.getCell(3).getStringCellValue()));
            }
            var sqlTemple2="insert into it_service_offering(id,tier1_id,tier2)values({0},{1},''{2}'');";

            var tier2List=new LinkedList<>(tier2);
            for(int i=0;i<tier2List.size();i++){
                tier2Map.put(tier2List.get(i).getValue(),i+1);
                //Files.writeString(Path.of("C:\\Users\\j32zhang\\workspace\\java\\starter\\output\\tier2.sql"),MessageFormat.format(sqlTemple2,i+1,tier1Map.get(tier2List.get(i).getKey()),tier2List.get(i).getValue())+System.lineSeparator(), StandardOpenOption.APPEND);
            }
        }

        {
            var tier3=new LinkedHashSet<Pair<String,String>>();
            for(int i=1;i<148;i++){
                var row=sheet.getRow(i);
                if(row.getCell(4)!=null&&!Strings.isNullOrEmpty(row.getCell(4).getStringCellValue())){
                    tier3.add(new Pair<>(row.getCell(3).getStringCellValue(),row.getCell(4).getStringCellValue()));
                }
            }
            var sqlTemple3="insert into it_service_offering_module(id,tier2_id,tier3)values({0},{1},''{2}'');";
            var tier3List=new LinkedList<>(tier3);
            for(int i=0;i<tier3List.size();i++){
                tier3Map.put(tier3List.get(i).getValue(),i+1);
                //Files.writeString(Path.of("C:\\Users\\j32zhang\\workspace\\java\\starter\\output\\tier3.sql"),MessageFormat.format(sqlTemple3,i+1,tier2Map.get(tier3List.get(i).getKey()),tier3List.get(i).getValue())+System.lineSeparator(), StandardOpenOption.APPEND);
            }
        }
        List<String> apps=new ArrayList<>();
        var workBook2=new XSSFWorkbook(Files.newInputStream(Paths.get("C:\\N-21AJPF3SS51N-Data\\j32zhang\\Downloads\\系统名称.xlsx")));
        var sheet2=workBook2.getSheet("字典数据");
        for(int i=1;i<159;i++){
            var row=sheet2.getRow(i);
            apps.add(row.getCell(3).getStringCellValue());
        }
        System.out.println(apps);
        Map<Integer,String> appMap=new LinkedHashMap<>();
        String pattern = "^*[0-9]{8}$";
        {
            /*nbs_domain varchar(16),
            service_tier1_id int unsigned,
            service_tier2_id int unsigned,
            service_tier3_id int unsigned,
            application varchar(16),
            service_owner_id bigint,
            business_analyst binint,
            solution_owner_id bigint,
            ims varchar(16),
            ams varchar(16),
            service_status varchar(2),
            service_description varchar(1024)*/
            Pattern p=Pattern.compile(pattern);

            var sqlTemple="insert into application_mapping(dept_id,service_tier1_id,service_tier2_id,service_tier3_id,application,service_owner_id,business_analyst_id,solution_owner_id,ims,ams,service_status,service_description)values"
                + "({0},{1},{2},{3},{4},{5},null,null,null,null,''{6}'',{7});";
            Map<String,String> depMap=new HashMap<>();
            depMap.put("Network & Cloud Platform","NWC");
            depMap.put("EntApp","Application");
            depMap.put("Ent App","Application");
            depMap.put("Enterprise Architecture","EA");
            depMap.put("IT Operation","Operations");
            Map<String,String> statusMap=new HashMap<>();
            statusMap.put("1","Planned");
            statusMap.put("2","Initial");
            statusMap.put("3","Active");
            for(int i=1;i<148;i++){
                System.out.println(i);
                var row=sheet.getRow(i);
                String appName=Optional.ofNullable(row.getCell(9)).map(XSSFCell::getStringCellValue).orElse(null);
                if(!Strings.isNullOrEmpty(appName)&&!apps.contains(appName)){
                    appMap.put(i,appName);
                }
                if(Strings.isNullOrEmpty(appName)){
                    appName=null;
                }else{
                    appName="'"+appName+"'";
                }
                String col7= "\""+row.getCell(8).getStringCellValue()+"\"";
                Matcher m=p.matcher(row.getCell(5).getStringCellValue());
                m.find();
                // Infra Infra​
                Files.writeString(Path.of("C:\\Users\\j32zhang\\workspace\\java\\sql-demo\\mybatis-demo\\mybatis-mysql-demo\\src\\main\\java\\com\\example\\demo\\controller\\application_mapping.sql"),
                    MessageFormat.format(sqlTemple,
                        Optional.ofNullable(sysDeptMapper.getIdByDeptName(depMap.getOrDefault(row.getCell(1).getStringCellValue(),row.getCell(1).getStringCellValue()))).orElseThrow(),
                        tier1Map.get(row.getCell(2).getStringCellValue()),
                        tier2Map.get(row.getCell(3).getStringCellValue()),
                        Optional.ofNullable(row.getCell(4)).map(XSSFCell::getStringCellValue).map(tier3Map::get).orElse(null),
                        //'change_sys_name'
                        appName,
                        sysUserMapper.getIdByNsnid(m.group()),
                        statusMap.get(row.getCell(7).getStringCellValue().split("-")[0]),
                        col7.replaceAll("\\n|\\r","")
                        )+System.lineSeparator(), StandardOpenOption.APPEND);
            }
            System.out.println(om.writeValueAsString(appMap));
        }



    /*var row=sheet.getRow(1);
    for(int j=1;j<10;j++){
      System.out.println(row.getCell(j).getStringCellValue());
    }*/
    /*for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){

    }*/
    }

    @GetMapping("/page")
    public PageInfo<Integer> pageTest() throws IOException {
        return PageHelper.startPage(1,10).doSelectPageInfo(()->sysDeptMapper.getId());
    }

    public static void main(String[] args) throws IOException {
        /*var workBook=new XSSFWorkbook(Files.newInputStream(Paths.get("C:\\N-21AJPF3SS51N-Data\\j32zhang\\Downloads\\BusinessService_Application Mapping_1124.xlsx")));
        var sheet=workBook.getSheet("service catalogue");
        for(int i=1;i<148;i++){
            var row=sheet.getRow(i);
            System.out.println(i);
            System.out.println(row.getCell(9).getStringCellValue());
        }*/
        /*String regex = "^*[0-9]{8}$";
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher("Wang, Harrison  69118401");
        while (m.find()) {
            // Get the group matched using group() method
            System.out.println(m.group());
        }*/
        ObjectMapper om=new ObjectMapper();
        String s="{\"2\":\"AD Provision Tool\",\"4\":\"NSB API Gateway Platform\",\"8\":\"Aries\",\"19\":\"Digital Media\",\"20\":\"CQMS\",\"23\":\"DES\",\"28\":\"Digital Media\",\"35\":\"MAD\",\"41\":\"Filed Service management platform\",\"46\":\"Goldmine\",\"50\":\"HR Portal\",\"57\":\"Intranet\",\"62\":\"IT Pop\",\"66\":\"Cabling\",\"67\":\"Cabling\",\"76\":\"Digital Media\",\"77\":\"Mobile voice service\",\"102\":\"Resource Dashboard\",\"110\":\"ASKIT\",\"113\":\"Mobile voice service\",\"119\":\"Mobile voice service\",\"125\":\"Software Management\",\"135\":\"UFIDA NC - R&D Superdeduction\",\"137\":\"Infirmary System\",\"139\":\"工会活动预定系统\",\"143\":\"Anti-Phishing Tools\"}";
        Map<Integer,String> appMap=om.readValue(s, new TypeReference<>() {});
        String sql1="INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark)VALUES(0,'";
        String sql2="', 'change_sys_name', '', '', 'Y', '0', 'admin', current_timestamp, 'admin', current_timestamp, '');";
        for(String name:appMap.values()){
            String line=sql1+name+"','"+name+sql2;
            System.out.println(line);
            Files.writeString(Path.of("C:\\Users\\j32zhang\\workspace\\java\\sql-demo\\mybatis-demo\\mybatis-mysql-demo\\src\\main\\java\\com\\example\\demo\\controller\\app.sql"),
                line+System.lineSeparator(), StandardOpenOption.APPEND);
        }


    }
    // 344 818 80
}
