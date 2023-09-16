# 에브리서치 EverySearch

```💡 서울여자대학교 전체부서 전화번호 검색 어플리케이션```


- **개발 기간** : 2019.12 ~ 2020.01
- **개발 인원** : 4명
    - UI/UX 디자이너 1명
    - 프론트 개발자 3명
- **IDE/Language** : Android Studio - Kotlin, Firebase
- **Github** : https://github.com/JangHJ/EverySearch_Project

<br>


### 🖥️ 구현한 기능 및 코드 설명
    
``` ❓ 구현한 기능의 자세한 코드 내용과 그에 대한 설명이 첨부되어 있습니다 ```

- **Firebase 로그인/회원가입 기능**
    1. **로그인**
    
    ```kotlin
    private fun emailLogin() {
        if (editText6.text.toString().isNullOrEmpty() || editText5.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "이메일과 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
            return
        }
        var email = editText6.text.toString()
        var password = editText5.text.toString()
        auth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email 로그인 성공", Toast.LENGTH_LONG).show()
                    moveMainPage(auth?.currentUser)
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
    private fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            val after_Intent = Intent(this, navigationreplace()::class.java)
            isLogin = true
            after_Intent.putExtra("isLogin", isLogin!!)
            startActivity(after_Intent)
            finish()
        }
    }
    
    //로그아웃 시에는 auth.signOut() 코드 사용
    //아래는 사용된 코드예시
    btnSignout.setOnClickListener{
        if(currentUser != null){
            auth!!.signOut() // 로그아웃 코드
            Toast.makeText(this, "로그아웃 되었습니다!", Toast.LENGTH_LONG).show()
            finish()
        }else{
            val nextIntent6 = Intent(this, login()::class.java)
            startActivity(nextIntent6)
        }
    }
    ```
    
    1. **회원가입**
    
    ```kotlin
    private fun emailSignup() {
        if (editEmail.text.toString().isNullOrEmpty() || editPwd.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "이메일과 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
            return
        }
        var email = editEmail.text.toString()
        var password = editPwd.text.toString()
    
        auth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "회원가입이 완료되었습니다", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, "회원가입 실패: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
    ```
    
    - **기능 설명**
    
    ```
    📖 사용자가 이메일과 비밀번호를 입력해 Firebase 로그인/회원가입 하는 코드입니다.
    (이메일, 비밀번호가 입력되지 않은 경우 Toast 메시지 출력)
    ```
    
    - **이 기술을 사용한 이유?**
    
    ```
    😣 코틀린을 처음 접한 후 만들게 된 앱입니다. 한정된 시간안에 빠르게 구현해야 했기 때문에
    유효성 검증 및 보안부분을 신경쓰지 않고 간편하게 테스트해볼 수 있는 Firebase를 활용했습니다.
    ```
    
<br>
    
- **학사일정 표시 기능 (Json 파싱)**
    
    ```kotlin
    private fun displayDateAndTask() {
    		val dateSet = SimpleDateFormat("MMMM dd, yyyy", Locale.US) // 월과 일, 연도까지 출력
    
    		// JSON 데이터 중 날짜 예시 : "September 06, 2023" 이므로
        // 현재 날짜를 "MMMM dd, yyyy" 형식의 Locale.US 문자열로 포맷팅
    		// JSON 날짜 비교용 **문자열 변수**
        val currentDateStr = dateSet.format(Date())
    
    		// 현재날짜 값 확인 로그
        Log.d("CurrentDate111111111", currentDateStr)
    
        val currentDate = dateSet.parse(currentDateStr) ?: Date() // 현재 날짜를 담은 Date 변수
    		// 출력용 현재 날짜 문자열 변수
        val dateText = SimpleDateFormat("MM월 dd일", Locale.KOREA).format(currentDate)
    
    		// 'ScheduleDate.json'파일을 불러와 파싱
        val assetManager = resources.assets
        val inputStream = assetManager.open("ScheduleDate.json")
        val jsonString = inputStream.bufferedReader().use {
            it.readText()
        }
        val jObject = JSONObject(jsonString)
        val jArray = jObject.getJSONArray("Key")
    
        // 현재 날짜보다 이전에 있는 마지막 학사일정 초기화
        var lastEventDate: Date? = null
    		// 학사일정 내용 담을 변수
        var lastEventText = ""
    
        // 학사일정 날짜 확인 및 출력
        for (i in 0 until jArray.length()) {
            val dateStr = jArray.getJSONObject(i).getString("날짜")
            // "MMMM dd, yyyy" 형식의 날짜 문자열을 파싱하여 전체 날짜로 남김
            val eventDate = dateSet.parse("$dateStr, ${currentDate.year + 1900}")
    
            if (currentDate.after(eventDate)) {
                val eventText = jArray.getJSONObject(i).getString("학사일정")
    
                // 현재 날짜보다 이전에 있는 학사일정 업데이트
                lastEventDate = eventDate
                lastEventText = eventText
            }
        }
    
        // 가장 최근 학사일정을 출력
        if (lastEventDate != null) {
            runOnUiThread {
                textView_date.text = dateText
                textView_task.text = lastEventText
                textView_date.visibility = View.VISIBLE
                textView_task.visibility = View.VISIBLE
            }
        } else {
            // 해당하는 학사일정이 없는 경우 "일정없음"을 출력
            runOnUiThread {
                textView_date.text = dateText
                textView_task.text = "일정없음"
                textView_date.visibility = View.VISIBLE
                textView_task.visibility = View.VISIBLE
            }
        }
    }
    ```
    
    - **기능 설명**
    
    ```
    📖 JSON 파일에서 학사 일정 데이터를 가져오고, 현재 날짜와 비교하여 가장 최근의 학사일정을 찾아 보여주는 코드입니다.
    ```

    <br>
    
- **공공데이터포털 API 활용 (학교 재적학생 확인)**
    
    ```kotlin
    private fun getStudentCountFromAPI() : String{
        try {
            val urlBuilder =
                StringBuilder("http://openapi.academyinfo.go.kr/openapi/service/rest/StudentService/getComparisonEnrolledStudentCrntSt")
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + serviceKey)
            urlBuilder.append(
                "&" + URLEncoder.encode(
                    "pageNo",
                    "UTF-8"
                ) + "=" + URLEncoder.encode("1", "UTF-8")
            )
            urlBuilder.append(
                "&" + URLEncoder.encode(
                    "numOfRows",
                    "UTF-8"
                ) + "=" + URLEncoder.encode("999", "UTF-8")
            )
            urlBuilder.append(
                "&" + URLEncoder.encode(
                    "schlId",
                    "UTF-8"
                ) + "=" + URLEncoder.encode("0000126", "UTF-8")
            )
            urlBuilder.append(
                "&" + URLEncoder.encode(
                    "svyYr",
                    "UTF-8"
                ) + "=" + URLEncoder.encode("2018", "UTF-8")
            )
    
            val url = URL(urlBuilder.toString())
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.setRequestProperty("Content-type", "application/json")
    
            val rd: BufferedReader = if (conn.responseCode >= 200 && conn.responseCode <= 300) {
                BufferedReader(InputStreamReader(conn.inputStream))
            } else {
                BufferedReader(InputStreamReader(conn.errorStream))
            }
    
            val sb = StringBuilder()
            var line: String?
    
            while (rd.readLine().also { line = it } != null) {
                sb.append(line)
            }
    
            rd.close()
            conn.disconnect()
    
            val responseXml = sb.toString()
    
            // XML 파싱
            val xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(InputSource(StringReader(responseXml)))
            xmlDoc.documentElement.normalize()
    
            // 학생 수 파싱
            val indctVal1Node = xmlDoc.getElementsByTagName("indctVal1").item(0)
    
            return indctVal1Node.textContent
        }  catch (e: Exception) {
            // 오류가 발생한 경우 0으로 반환
            return "0"
        }
    }
    ```
    
    - **기능 설명**

    ```📖 공공데이터포털의 API를 활용하여 학교의 재적학생 수를 확인하는 기능을 구현했습니다```

    [한국대학교육협의회 대학정보공시 학생 현황] (https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15037346) <br> 
    
    - **해당 방법을 사용한 이유?**
    
    ```
    📖 학교의 재적학생 수 정보를 공공데이터 포털을 통해 쉽게 제공받을 수 있고 데이터의 정확성과
    학교 최신 정보를 얻을 수 있기 때문에 수동 업데이트나 데이터 관리의 번거로움을 줄일 수 있을 거라 생각했습니다.
    ```
    

### 🖥️ 회고

```
추가되면 좋을 것 같은 기능 + 아쉬운 점
```

**학교 선택에 따른 정보 불러오기**

```
현재는 서울여자대학교의 학사일정이나 재적학생 정보만 불러올 수 있도록 구현되어 있기 때문에
사용자의 다양성을 고려한다면 선택한 학교에 따라 그에 맞는 데이터를 불러올 수 있다면 앱의 유용성이 더 좋아질 것 같다.
```

**자주 찾는 전화번호 즐겨찾기**

```Firebase database를 사용해 로그인한0 사용자가 전화번호를 즐겨찾기하여 따로 볼 수 있는 기능이 있으면 편리성을 더욱 높일 수 있을 것 같다.```

- **아쉬웠던 점**
    
    **데이터 업데이트 어려움 :** 학교의 학사일정이 변경될 때마다 JSON 파일을 업데이트해야 하므로 업데이트 관리에 번거로움이 생긴다는 점
    
    **실시간 정보 부재** : 정적인 데이터를 사용하므로 학사일정의 실시간 정보를 제공하기 어려운 점
