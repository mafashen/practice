package com.mafashen.java;

import com.alibaba.fastjson.JSONObject;
import com.mafashen.jvm.gc.* ;
import com.souche.toolize.finance.product.model.dto.BriefLayeredPlanDTO;
import com.souche.toolize.finance.product.model.dto.BriefUsedProductDTO;
import com.souche.toolize.finance.product.model.dto.LoanAmountDTO;
import com.souche.toolize.finance.product.model.dto.UsedCarProductBriefPlanDTO;
import com.souche.toolize.finance.product.model.dto.UsedLeaseInfoDTO;
import com.souche.toolize.finance.product.model.dto.UsedPlanDetailDTO;
import com.souche.toolize.finance.product.model.dto.UsedProductDealDTO;
import com.souche.toolize.finance.product.model.dto.UsedProductInsuranceDTO;
import com.souche.toolize.finance.product.model.dto.UsedProductWithCarInfoDTO;
import com.souche.toolize.finance.product.model.param.QueryDetailedPlanParam;
import com.souche.toolize.finance.product.model.param.QueryUsedProductParam;
import com.souche.toolize.finance.product.model.param.UsedLoanApplyParam;
import com.souche.toolize.finance.product.service.ToolizeUsedProductService;
import com.souche.toolize.finance.product.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 生成的class文件中没有使用到的import不会出现
 * @author mafashen
 * @since 2019-10-29.
 */
public class TestNotUsedImport {


    public static void main(String[] args) {
        System.out.println("TestNotUsedImport");

        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        ToolizeUsedProductService service = ctx.getBean(ToolizeUsedProductService.class);
//        ToolizeUsedProductService service = new ToolizeUsedProductServiceImpl();
        QueryDetailedPlanParam param = new QueryDetailedPlanParam();
        param.setProductId("394654");
        Result<UsedPlanDetailDTO> result = service.queryPlanDetail(param);
        System.out.println(JSONObject.toJSONString(result.getData()));
    }

   static class ToolizeUsedProductServiceImpl implements ToolizeUsedProductService{

        @Override
        public Result<List<LoanAmountDTO>> queryLoanAmount(Long productId) {
            return null;
        }

        @Override
        public Result<UsedLeaseInfoDTO> queryProductLeaseInfo(Long productId) {
            return null;
        }

        @Override
        public Result<BriefUsedProductDTO> queryBriefPlanByCarId(String carId) {
            return null;
        }

        @Override
        public Result<BriefUsedProductDTO> queryBriefPlanByProductId(Long productId) {
            return null;
        }

        @Override
        public Result<UsedPlanDetailDTO> queryPlanDetail(QueryDetailedPlanParam queryDetailedPlanParam) {
            return Result.success(new UsedPlanDetailDTO());
        }

        @Override
        public Result<List<BriefUsedProductDTO>> queryBriefPlanListByCarId(String carId) {
            return null;
        }

        @Override
        public Result<List<BriefUsedProductDTO>> queryBriefPlanListByProductId(Long productId) {
            return null;
        }

        @Override
        public Result<Boolean> checkValidity(Long productId) {
            return null;
        }

        @Override
        public Result<Boolean> checkValidity(String carId) {
            return null;
        }

        @Override
        public Result<Boolean> checkProductStatus(String carId, Integer prepaidRate) {
            return null;
        }

        @Override
        public Result<UsedLoanApplyParam> queryLoanApplyParamByCarId(String carId) {
            return null;
        }

        @Override
        public Result<UsedLoanApplyParam> queryLoanApplyParamByProductId(Long productId) {
            return null;
        }

        @Override
        public Result<List<BriefLayeredPlanDTO>> queryBriefLayeredPlan(String carId) {
            return null;
        }

        @Override
        public Result<UsedProductDealDTO> queryProductDealInfoByCarId(String carId) {
            return null;
        }

        @Override
        public Result<UsedProductInsuranceDTO> queryInsuranceByCarId(String carId) {
            return null;
        }

        @Override
        public Result<com.souche.optimus.common.page.Page<UsedProductWithCarInfoDTO>> queryProductPageByParam(QueryUsedProductParam param) {
            return null;
        }

        @Override
        public Result<UsedProductWithCarInfoDTO> queryProductByProductId(Long productId) {
            return null;
        }

        @Override
        public Result<String> queryShopCodeByCarId(String carId) {
            return null;
        }

        @Override
        public Result<String> queryShopCodeByProductId(Long productId) {
            return null;
        }

        @Override
        public Result<List<UsedCarProductBriefPlanDTO>> queryProductBriefPlan(List<String> carIdList) {
            return null;
        }

        @Override
        public Result<List<String>> queryCarIdList(Integer page, Integer pageSize) {
            return null;
        }
    }
}
