package com.example.kotlin.kotlin.`object`


fun showStatus(c: Coupon, u: User) {
    val status = Coupon.getCouponStatus(c, u) //可以根据业务，将数据记录下来
    when (status) {
        is StatusNotFetched -> {
        }
        is StatusFetched -> {
        }
        is StatusUsed -> {
        }
        is StatusExpired -> {
        }
        is StatusUnAvailable -> {
        }
    }
}

/**
 * 优惠券
 */
sealed class Coupon {
    companion object {
        //类型
        const val CashType = "CASH"
        const val DiscountType = "DISCOUNT"
        const val GiftType = "GIFT"

        //状态
        private const val NotFetched = 1 //未领取
        private const val Fetched = 2 //已领取但未使用
        private const val Used = 3 //已使用
        private const val Expired = 4 //已过期
        private const val UnAvailable = 5 //已失效

        private fun fetch(c: Coupon, u: User) = true
        private fun used(c: Coupon, u: User) = false
        private fun isExpired(c: Coupon) = false
        private fun isAvailable(c: Coupon) = false

        fun getStatus(c: Coupon, u: User): Int = when {
            isAvailable(c) -> UnAvailable
            isExpired(c) -> Expired
            fetch(c, u) -> Fetched
            used(c, u) -> Used
            else -> NotFetched
        }

        fun getCouponStatus(c: Coupon, u: User): CouponStatus = when {
            isAvailable(c) -> StatusUnAvailable(c)
            isExpired(c) -> StatusExpired(c)
            fetch(c, u) -> StatusFetched(c, u)
            used(c, u) -> StatusUsed(c, u)
            else -> StatusNotFetched(c)
        }
    }
}

sealed class CouponStatus()
data class StatusNotFetched(val c: Coupon) : CouponStatus()
data class StatusFetched(val c: Coupon, val u: User) : CouponStatus()
data class StatusUsed(val c: Coupon, val u: User) : CouponStatus()
data class StatusExpired(val c: Coupon) : CouponStatus()
data class StatusUnAvailable(val c: Coupon) : CouponStatus()

// 满减券
class CashCoupon(
    val id: Long,
    val type: String,
    val leastCost: Long, //最低消费
    val reduceCost: Long //优惠多少
) : Coupon()

// 打折券
class DiscountCoupon(
    val id: Long,
    val type: String,
    val discount: Int //折扣
) : Coupon()

// 礼品券
class GiftCoupon(
    val id: Long,
    val type: String,
    val gift: String //礼品
) : Coupon()

data class User(val id: Long)