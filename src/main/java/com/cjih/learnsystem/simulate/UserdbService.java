package com.cjih.learnsystem.simulate;

import com.jfinal.club.common.model.NumberUser;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

public class UserdbService {

    public static final UserdbService me = new UserdbService();
    private NumberUser dao = new NumberUser().dao();

    public Page<NumberUser> paginate(int pageNum, String query) {
        return dao.paginate(pageNum, PropKit.getInt("pageSize"), "select *", "from tnumber_user where 1=1 "
                +query+"order by id desc");
    }

    public NumberUser findById(int id) {
        return dao.findById(id);
    }

    public NumberUser edit(int id) {
        return dao.findById(id);
    }

    public Ret save(NumberUser bm) {
        bm.save();
        return Ret.ok();
    }

    /**
     * 修改数据
     */
    public Ret update(NumberUser nu) {
        nu.update();
        return Ret.ok("msg", "用户消息更新成功");
    }

    /**
     * 删除数据
     */
    public Ret delete(final long id) {
        NumberUser bm = dao.findById(id);
        if(bm.delete()) {
            return Ret.ok("msg", "用户消息 删除成功");
        }
        else {
            return Ret.ok("msg", "用户消息 删除失败");
        }
    }

}
