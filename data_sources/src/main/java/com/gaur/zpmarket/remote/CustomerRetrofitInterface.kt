package com.gaur.zpmarket.remote


import com.gaur.zpmarket.pagination.orders.OrderPaginationResponse
import com.gaur.zpmarket.pagination.search.response.SearchPagingResponse
import com.gaur.zpmarket.remote.response_customer.auth.login.CustomerLoginBody
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegisterBody
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegistrationResponse
import com.gaur.zpmarket.remote.response_customer.cart.CartPostBody
import com.gaur.zpmarket.remote.response_customer.home.HomeResponse
import com.gaur.zpmarket.remote.response_customer.order.PostOrder
import com.gaur.zpmarket.remote.response_customer.payment.OrderIdResponse
import com.gaur.zpmarket.remote.response_customer.payment.OrderPrice
import com.gaur.zpmarket.remote.response_customer.profile.CustomerProfileResponse
import com.gaur.zpmarket.remote.response_seller.ServerMessage
import retrofit2.Response
import retrofit2.http.*

interface CustomerRetrofitInterface {


    /** Authentication  Login**/
    @POST("custumer/login")
    suspend fun customerLogin(@Body customerLoginBody: CustomerLoginBody): Response<CustomerRegistrationResponse>

    /** Authentication Registration* */
    @POST("custumer/register")
    suspend fun customerRegistration(@Body customerRegisterBody: CustomerRegisterBody): Response<CustomerRegistrationResponse>


    /** Home Page Details**/
    @GET("ecom/seller/home/page_details")
    suspend fun getHomePageDetails(): Response<HomeResponse>

    /** Profile  **/
    @GET("custumer/profile/{customerId}")
    suspend fun getCustomerProfile(@Path("customerId") id: String): Response<CustomerProfileResponse>


    /**  Add to Cart  **/
    @POST("customer/cart")
    suspend fun postCart(@Body cart: CartPostBody): Response<ServerMessage>

    /**  Post Order  **/
    @POST("customer/order")
    suspend fun postOrder(@Body order: PostOrder): Response<ServerMessage>

    /**  payment order id creation **/
    @POST("customer/init_order")
    suspend fun getOrderId(@Body amount:OrderPrice):Response<OrderIdResponse>


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
        @Query("q") query: String, @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<SearchPagingResponse>

}
