package com.sba.payment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sba.payment.model.BatchPayment;
import com.sba.payment.model.Payment;

@Mapper
public interface PaymentMapper {

	@Insert("insert into sba_payment.payment(courseId,userName,mentorName,startDate,endDate,fee) values(#{courseId},#{userName},#{mentorName},#{startDate},#{endDate},#{fee})")
	@SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
	void addPayment(Payment payment);

	@Select("SELECT cost FROM sba_payment.payment where courseId = #{courseid}")
	Float checkCost(@Param("courseid") Integer courseid);
	
	@Select("SELECT id, courseId, cost/fee*100 as schedule,startDate, endDate, fee FROM sba_payment.payment where cost/fee*100 != 100")
	List<BatchPayment> batchpayment();
	// update pahse
	@Update("update sba_payment.payment set cost=fee*#{phase} where courseid = #{id}")
	void updatePayment(@Param("id") Integer id,@Param("phase") Float phase);

}
