package com.efojug.chatwithmepro.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.efojug.chatwithmepro.ChatDataManager
import com.efojug.chatwithmepro.ChatRoom
import com.efojug.chatwithmepro.databinding.FragmentChatRoomBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ChatRoomFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatRoomFragment : Fragment() {
    // TODO: Rename and change types of parameters
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentChatRoomBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            ChatRoom(list = ChatDataManager.getAllChatData())
        }
        return binding.root
    }
}