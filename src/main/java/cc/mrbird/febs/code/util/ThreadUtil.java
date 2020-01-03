package cc.mrbird.febs.code.util;

import cc.mrbird.febs.code.entity.Code;
import cc.mrbird.febs.code.entity.CodeCategroy;
import cc.mrbird.febs.code.mapper.CodeMapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

/**
 * 开启新增二维码的多线程
 * @author lizf
 * @datetime 2019/11/8
 */
public class ThreadUtil extends Thread{

    private CodeCategroy code;

    private CodeMapper codeMapper;

    public ThreadUtil(CodeCategroy code,CodeMapper codeMapper) {
        this.code = code;
        this.codeMapper = codeMapper;
    }
    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     */
    @Override
    public void run() {
        for (int i = 0; i < code.getNumber(); i++) {
            Code c = new Code();
            c.setId(String.valueOf(IdWorker.getId()));
            c.setCategroyId(code.getId());
            c.setStatus(0);
            codeMapper.insert(c);
        }
    }
}
