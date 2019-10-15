package com.proxypool.controller;

import com.proxypool.base.ResultData;
import com.proxypool.kindlebook.KdlBookProcessor;
import com.proxypool.kindlebook.MeBookPipeline;
import com.proxypool.kindlebook.MebookProcessor;
import com.proxypool.mail.service.MailService;
import com.proxypool.recruit.Job51Processor;
import com.proxypool.recruit.RecruitInfoPipeline;
import com.proxypool.service.MeBookService;
import com.proxypool.service.RecruitInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: muse-pay
 * @description: 抓取信息控制类
 * @author: Vincent
 * @create: 2018-10-15 12:06
 **/
@RequestMapping("/crawl")
@RestController
public class CrawlController {
    private Logger log = LoggerFactory.getLogger("CrawlController");

    @Autowired
    private RecruitInfoPipeline recruitInfoPipeline;

    @Autowired
    private Job51Processor proxy51jobProcessor;

    @Autowired
    private RecruitInfoService recruitInfoService;

    @Autowired
    private MebookProcessor mebookProcessor;

    @Autowired
    private MeBookPipeline meBookPipeline;

    @Autowired
    private KdlBookProcessor kdlBookProcessor;

    @Autowired
    private MailService mailService;

    @Autowired
    private MeBookService meBookService;

    /**
     * 定时抓取JL
     *
     * @return ResultData
     */
    @Scheduled(cron = "${RECRUIT_CRON}")
    public void recruit() {
        try {
            // 处理页面的时间间隔
            proxy51jobProcessor.setInterval(1000).setThreadCount(2);
            proxy51jobProcessor.execute(recruitInfoPipeline, false);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("抓取JL异常=" + e.getMessage());
        }
    }

    /**
     * 抓取KDLBook电子书
     */
    @Scheduled(cron = "${CRAWL_BOOK_CRON}")
    public void kdlbookProcessor() {
        try {
            kdlBookProcessor.setInterval(2000).setThreadCount(2).execute(meBookPipeline, null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("抓取KDLBook电子书异常=" + e.getMessage());
        }
    }


    /**
     * 定时发送邮件
     */
    @RequestMapping("/sendMail")
    @Scheduled(cron = "${EMAIL_CRON}")
    public void monitor() {
        try {
            // 查询数据数量
            int count = meBookService.getCount();
            int todayCount = meBookService.getTodayCount();
            String content = "当前图书总数量：" + count + ", 今日数量：" + todayCount;

            int zpCount = recruitInfoService.getCount();
            int zpTodayCount = recruitInfoService.getTodayCount();
            content += "\n<br>当前ZP总数量：" + zpCount + ", 今日数量：" + zpTodayCount;

            mailService.sendSimpleMail("vicat2019@163.com", "数据监测", content);
        } catch (Exception e) {
            log.error("发送数据监测邮件异常", e);
        }

    }


}
