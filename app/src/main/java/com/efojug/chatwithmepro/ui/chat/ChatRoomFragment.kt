package com.efojug.chatwithmepro.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.get
import com.efojug.chatwithmepro.ChatDataManager
import com.efojug.chatwithmepro.ChatRoom
import com.efojug.chatwithmepro.R
import com.efojug.chatwithmepro.databinding.FragmentChatRoomBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
    ): View? {
        val binding = FragmentChatRoomBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            ChatRoom(list = ChatDataManager.getAllChatData())
        }
        return binding.root
    }
}