package com.teamfillin.fillin.presentation.my

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.teamfillin.fillin.presentation.add.Photos
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseUserInfo
import com.teamfillin.fillin.data.service.MyPagePhotoService
import com.teamfillin.fillin.data.service.UserService
import com.teamfillin.fillin.databinding.ActivityMyPageBinding
import com.teamfillin.fillin.enqueueUtil
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MyPageActivity : BindingActivity<ActivityMyPageBinding>(R.layout.activity_my_page) {
    @Inject
    lateinit var userService: UserService
    lateinit var myPagePhotoService: MyPagePhotoService //TODO @Inject 한번더 써야되나?


    private lateinit var adapter: MyPagePhotoRecyclerViewAdapter
    var photoDatas = listOf<Photos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializelist()
        initPhotoRecyclerView()
        showUserInfo()
        showUserPhoto()
    }

    private fun showUserPhoto(){ //TODO 이 함수 안에 바꿔야됨..
        val call: Call<BaseResponse<ResponseUserInfo>> = userService.getUserInfo()

        call.enqueueUtil(
            onSuccess={data->
                binding.tvNickname.text= data?.data.user.id.toString()
                Glide.with(this@MyPageActivity)
                    .load(data?.data.user.imageUrl)
                    .circleCrop()
                    .into(binding.ivProfile)
            }
        )

//        call.enqueue(object: Callback<BaseResponse<ResponseUserInfo>>{
//            override fun onResponse(
//                call: Call<BaseResponse<ResponseUserInfo>>,
//                response: Response<BaseResponse<ResponseUserInfo>>
//            ) {
//                if(response.isSuccessful){
//                    val userData=response.body()?.data
//                    Log.d("유저정보","${userData?.user?.id}")
//                    binding.tvNickname.text=userData?.user?.nickname
//                    Glide.with(this@MyPageActivity)
//                        .load(userData?.user?.imageUrl)
//                        .circleCrop()
//                        .into(binding.ivProfile)
//
//                } else{
//                    Toast.makeText(this@MyPageActivity,"유저정보 조회실패",Toast.LENGTH_SHORT).show()
//                }
//            }
//            override fun onFailure(call: Call<BaseResponse<ResponseUserInfo>>, t: Throwable) {
//                Log.e("NetworkTest","error:$t")
//            }
//
//        })
    }

    private fun showUserInfo(){
        val call: Call<BaseResponse<ResponseUserInfo>> = userService.getUserInfo()

        call.enqueue(object: Callback<BaseResponse<ResponseUserInfo>>{
            override fun onResponse(
                call: Call<BaseResponse<ResponseUserInfo>>,
                response: Response<BaseResponse<ResponseUserInfo>>
            ) {
                if(response.isSuccessful){
                    val userData=response.body()?.data
                    Log.d("유저정보","${userData?.user?.id}")
                    binding.tvNickname.text=userData?.user?.nickname
                    Glide.with(this@MyPageActivity)
                        .load(userData?.user?.imageUrl)
                        .circleCrop()
                        .into(binding.ivProfile)

                } else{
                    Toast.makeText(this@MyPageActivity,"유저정보 조회실패",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<BaseResponse<ResponseUserInfo>>, t: Throwable) {
                Log.e("NetworkTest","error:$t")
            }

        })

    }

    private fun initializelist() {
        photoDatas = listOf(
            Photos(R.drawable.and_photo_rectangle),
            Photos(R.drawable.and_photo_rectangle),
            Photos(R.drawable.and_photo_rectangle),
            Photos(R.drawable.and_photo_rectangle),
            Photos(R.drawable.and_photo_rectangle),
            Photos(R.drawable.and_photo_rectangle)
        )
    }

    private fun initPhotoRecyclerView() {
        adapter = MyPagePhotoRecyclerViewAdapter()
        adapter.replaceList(photoDatas)
        binding.rvMyPage.adapter = adapter
        val gridLayoutManager = GridLayoutManager(this, 3)
        binding.rvMyPage.layoutManager = gridLayoutManager
    }


    private fun setProfile() {
        Glide.with(this)
            .load(R.drawable.profile)
            .circleCrop()
            .into(binding.ivProfile)
        //TODO by현지: 확장함수에 CircleCrop등 기능 추가되면 바꿔보기
    }
}