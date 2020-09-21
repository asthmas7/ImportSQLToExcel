package web;


import enity.DataInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.ImportExcelUtil;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

@Controller
public class Control {
    private static final long serialVersionUID = 1L;
    @RequestMapping("/import")
    public String Import(){
        System.out.println("Import");
        return null;
    }
    @RequestMapping("/export")
    public String Export(){
        System.out.println("Export");
        return null;
    }
    @ResponseBody
    @RequestMapping(value = "/export/process" ,method = RequestMethod.POST)
    public String ExportProcess(@RequestBody DataInfo info,HttpServletRequest request) throws SQLException {

        System.out.println(info.getIp());
        System.out.println(info.getPort());
        System.out.println(info.getDatabase());
        System.out.println(info.getUsername());
        System.out.println(info.getPassword());
        System.out.println(info.getSheetName());
        String file= ImportExcelUtil.exportFromSQLToExcel(request,info.getIp(), info.getPort(), info.getDatabase(),info.getUsername(),info.getPassword(),info.getSheetName());

        return file;
    }

}
