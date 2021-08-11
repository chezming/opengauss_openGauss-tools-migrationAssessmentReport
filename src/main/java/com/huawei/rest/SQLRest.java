package com.huawei.rest;

import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huawei.jdbc.DatabaseType;
import com.huawei.payroll.AssessmentCache;
import com.huawei.payroll.DefinitionPathCache;
import com.huawei.shell.ExecUtil;
import com.huawei.util.FileUtil;

@RestController
@RequestMapping("/sql")
public class SQLRest {
    @PostMapping(value = "convert")
    public Map.Entry<Boolean, String> sqlConvert(@RequestBody() Map<String, String> sql) {
        return ExecUtil.sqlConvert(sql.get("sql"), DatabaseType.ORACLE);
    }

    @PostMapping(value = "modify")
    public String sqlModify(@RequestBody() Map<String, String> map) {
        final String sql = map.get("sql");
        final String id = map.get("id");
        final Optional<String> ddlPath = DefinitionPathCache.INSTANCE.getDDLPath(id);
        if (!ddlPath.isPresent()) {
            return String.format("can not find DDL path, id:%s", id);
        } else {
            final Optional<String> s = FileUtil.WriteFile(ddlPath.get(), sql);
            if (!s.isPresent()) {
                // success
                AssessmentCache.refresh(id, sql);
            }
            return s.orElse("");
        }
    }
}
