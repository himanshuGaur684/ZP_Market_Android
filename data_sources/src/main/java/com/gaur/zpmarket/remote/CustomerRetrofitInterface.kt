package com.gaur.zpmarket.remote

import com.gaur.zpmarket.pagination.orders.OrderPaginationResponse
import com.gaur.zpmarket.pagination.search.response.SearchPagingResponse
import com.gaur.zpmarket.remote.response_customer.auth.login.CustomerLoginDTO
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegisterDTO
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegistrationResponse
import com.gaur.zpmarket.remote.response_customer.cart.CartPostDTO
import com.gaur.zpmarket.remote.response_customer.home.CustomerHomeDTO
import com.gaur.zpmarket.remote.response_customer.order.PostOrder
import com.gaur.zpmarket.remote.response_customer.payment.OrderIdDTO
import com.gaur.zpmarket.remote.response_customer.payment.OrderPriceDTO
import com.gaur.zpmarket.remote.response_customer.profile.CustomerProfileDTO
import com.gaur.zpmarket.remote.response_seller.ServerMessageDTO
import retrofit2.Response
import retrofit2.http.*

interface CustomerRetrofitInterface {

    /** Authentication  Login**/
    @POST("custumer/login")
    suspend fun customerLogin(@Body customerLoginDTO: CustomerLoginDTO): Response<CustomerRegistrationResponse>

    /** Authentication Registration* */
    @POST("custumer/register")
    suspend fun customerRegistration(@Body customerRegisterDTO: CustomerRegisterDTO): Response<CustomerRegistrationResponse>

    /** Home Page Details**/
    @GET("ecom/seller/home/page_details")
    suspend fun getHomePageDetails(): Response<CustomerHomeDTO>

    /** Profile  **/
    @GET("custumer/profile/{customerId}")
    suspend fun getCustomerProfile(@Path("customerId") id: String): Response<CustomerProfileDTO>

    /**  Add to Cart  **/
    @POST("customer/cart")
    suspend fun postCart(@Body cart: CartPostDTO): Response<ServerMessageDTO>

    /**  Post Order  **/
    @POST("customer/order")
    suspend fun postOrder(@Body order: PostOrder): Response<ServerMessageDTO>

    /**  payment order id creation **/
    @POST("customer/init_order")
    suspend fun getOrderId(@Body amount: OrderPriceDTO): Response<OrderIdDTO>

    /** Getting Order pagination **/
    @GET("customer/orders/{customerId}")
    suspend fun getOrderPaginatedList(
        @Path("customerId") customerId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<OrderPaginationResponse>

    /**  Search Query for Products  **/
    @GET("products/search")
    suspend fun searchProductQuery(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<SearchPagingResponse>
}
