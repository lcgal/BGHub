package com.example.bghub.Views.Fragments

import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.bghub.Models.Chat.FriendlyMessage
import com.example.bghub.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_chat.*


class ChatFragment : Fragment()
{
    private val TAG = "GameRoomChat"
    lateinit var  Chat_Root: String
    lateinit var  Chat_ID: String
    private var mUsername: String? = null
    private var mPhotoUrl: String? = null
    private var mChatReference: DatabaseReference? = null
    private var mSharedPreferences: SharedPreferences? = null
    //private val mSignInClient: GoogleSignInClient? = null

    private var mSendButton: Button? = null
    private var mMessageRecyclerView: RecyclerView? = null
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var mProgressBar: ProgressBar? = null
    private var mMessageEditText: EditText? = null
    private var mAddMessageImageView: ImageView? = null

    // Firebase instance variables
    private var mFirebaseAuth: FirebaseAuth? = null
    private var mFirebaseUser: FirebaseUser? = null
    private var mFirebaseDatabaseReference: DatabaseReference? = null
    private var mFirebaseAdapter: FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO
        //mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        // Initialize Firebase Auth
        initFireBase()

        // Initialize ProgressBar and RecyclerView.
        initViews()

        // New child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().reference

        initAdapter()

        setupAdapter()

        mMessageEditText = messageEditText as EditText
        mMessageEditText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.toString().trim { it <= ' ' }.length > 0) {
                    mSendButton!!.isEnabled = true
                } else {
                    mSendButton!!.isEnabled = false
                }
            }
            override fun afterTextChanged(editable: Editable) {}
        })

        mSendButton = sendButton as Button
        mSendButton!!.setOnClickListener(View.OnClickListener {
            sendMessage()
        })

        //TODO have some use for this button
        mAddMessageImageView = addMessageImageView as ImageView
        mAddMessageImageView!!.setOnClickListener(View.OnClickListener {
            // Select image for image message on click.
        })

    }

    override fun onPause() {
        mFirebaseAdapter!!.stopListening()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mFirebaseAdapter!!.startListening()
    }

//    fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater: MenuInflater = getMenuInflater()
//        inflater.inflate(R.menu.main_menu, menu)
//        return true
//    }

    fun initFireBase() {
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseUser = mFirebaseAuth!!.getCurrentUser()
        mUsername = mFirebaseUser!!.displayName
        if (mFirebaseUser!!.photoUrl != null) {
            mPhotoUrl = mFirebaseUser!!.photoUrl.toString()
        }
    }

    fun initViews() {
        mProgressBar = progressBar
        mMessageRecyclerView = messageRecyclerView
        mLinearLayoutManager = LinearLayoutManager(activity)
        mLinearLayoutManager!!.setStackFromEnd(true)
        mMessageRecyclerView!!.setLayoutManager(mLinearLayoutManager)
    }


    fun initAdapter() {
        val parser: SnapshotParser<FriendlyMessage?> = object : SnapshotParser<FriendlyMessage?> {
            override fun parseSnapshot(dataSnapshot: DataSnapshot): FriendlyMessage {
                val friendlyMessage: FriendlyMessage? = dataSnapshot.getValue(FriendlyMessage::class.java)
                if (friendlyMessage != null) {
                    friendlyMessage.setId(dataSnapshot.key)
                    return friendlyMessage
                }
                return FriendlyMessage()
            }
        }

        mChatReference = mFirebaseDatabaseReference!!
                .child(Chat_Root)
                .child(Chat_ID)
        val options = FirebaseRecyclerOptions.Builder<FriendlyMessage>()
                .setQuery(mChatReference!!, parser)
                .build()

        mFirebaseAdapter = object : FirebaseRecyclerAdapter<FriendlyMessage?, MessageViewHolder?>(options) {
            override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MessageViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                return MessageViewHolder(inflater.inflate(R.layout.item_message, viewGroup, false))
            }

            override fun onBindViewHolder(viewHolder: MessageViewHolder,
                                          position: Int,
                                          friendlyMessage: FriendlyMessage) {
                mProgressBar!!.visibility = ProgressBar.INVISIBLE
                if (friendlyMessage.text != null) {
                    viewHolder.messageTextView.text = friendlyMessage.text
                    viewHolder.messageTextView.visibility = TextView.VISIBLE
                }
                viewHolder.messengerTextView.text = friendlyMessage.name
                if (friendlyMessage.photoUrl == null) {
                    viewHolder.messengerImageView.setImageDrawable(activity?.let {
                        ContextCompat.getDrawable(it,
                                R.drawable.ic_account_circle_black_36dp)
                    })
                } else {
                    Picasso.get()
                            .load(friendlyMessage.photoUrl)
                            .placeholder(R.drawable.ic_account_circle_black_36dp)
                            .error(R.drawable.ic_account_circle_black_36dp)
                            .fit()
                            .centerCrop()
                            .into(viewHolder.messengerImageView)
//                    Glide.with(this@MainActivity)
//                            .load(friendlyMessage.photoUrl)
//                            .into(viewHolder.messengerImageView)
                }
            }
        } as FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>?
    }

    fun setupAdapter() {
        mFirebaseAdapter!!.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                val friendlyMessageCount = mFirebaseAdapter!!.itemCount
                val lastVisiblePosition = mLinearLayoutManager!!.findLastCompletelyVisibleItemPosition()
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        positionStart >= friendlyMessageCount - 1 &&
                        lastVisiblePosition == positionStart - 1) {
                    mMessageRecyclerView!!.scrollToPosition(positionStart)
                }
            }
        })

        mMessageRecyclerView!!.adapter = mFirebaseAdapter

    }

    fun sendMessage() {
        val friendlyMessage = FriendlyMessage(mMessageEditText!!.text.toString(),
                mUsername,
                mPhotoUrl,
                null /* no image */)
        mChatReference?.push()?.setValue(friendlyMessage)
        mMessageEditText!!.setText("")
    }


    /**
     * ViewHolder for the messages
     *
     * It's able to listen to the click because it implements the GameListAdapter.OnGameRowListener interface, and it passes it's own listener to the adapter @see onViewCreated.
     * The action argument is defined by what part of the card was clicked and what we should do.
     * @author lcgal
     * @param Models.Games.Game
     */
    class MessageViewHolder(v: View?) : RecyclerView.ViewHolder(v!!) {
        var messageTextView: TextView
        var messengerTextView: TextView
        var messengerImageView: CircleImageView

        init {
            messageTextView = itemView.findViewById<View>(R.id.messageTextView) as TextView
            messengerTextView = itemView.findViewById<View>(R.id.messengerTextView) as TextView
            messengerImageView = itemView.findViewById<View>(R.id.messengerImageView) as CircleImageView
        }
    }

    fun initialize(chatId : String, chatRoot : String) {
        Chat_ID = chatId
        Chat_Root = chatRoot
    }

    companion object {
        @JvmStatic
        fun newInstance(chatId : String, chatRoot : String) : ChatFragment {
            val fragment = ChatFragment()
            fragment.initialize(chatId,chatRoot)
            return fragment
        }
    }
}