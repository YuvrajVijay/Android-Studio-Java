import 'package:flutter/material.dart';

void main() => runApp(MyApp());


class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    Color hexToColor(String code) {
      return new Color(int.parse(code.substring(1, 7), radix: 16) + 0xFF000000);
    }

    return MaterialApp(
     /* debugShowCheckedModeBanner: false,
      title: "Welcome to Flutter",
      home: new Material(
        child: new Container (
          padding: const EdgeInsets.all(30.0),
          color: Colors.white,
          child: new Container(
            child: new Center(
              child: new Column(
                children : [
                  new Padding(padding: EdgeInsets.only(top: 140.0)),
                  new Text('Beautiful Flutter TextBox',
                    style: new TextStyle(color: hexToColor("#F2A03D"), fontSize: 25.0),),
                  new Padding(padding: EdgeInsets.only(top: 50.0)),
                  new TextFormField(
                    decoration: new InputDecoration(
                      labelText: "Enter Email",
                      fillColor: Colors.white,
                      border: new OutlineInputBorder(
                        borderRadius: new BorderRadius.circular(25.0),
                        borderSide: new BorderSide(
                        ),
                      ),
                      //fillColor: Colors.green
                    ),
                    validator: (val) {
                      if(val.length==0) {
                        return "Email cannot be empty";
                      }else{
                        return null;
                      }
                    },
                    keyboardType: TextInputType.emailAddress,
                    style: new TextStyle(
                      fontFamily: "Poppins",
                    ),
                  ),
                ],
              ),
            ),

          ),

        ),

      ),*/
      home : _fulhome(),
    );

  }
}

class _fulhome extends StatelessWidget {

  @override
  Widget build(BuildContext context) {

    void _navigateTosecondscreen(BuildContext context){
      Navigator.push(context, MaterialPageRoute(builder: (context)=> secondscreen(),));
    }

    return new Scaffold(
      appBar: new AppBar(
        title: new Text("Task manager"),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed:(){ _navigateTosecondscreen(context);},
        tooltip: 'Add task',
        child: Icon(Icons.add),
      ),
    );

  }


}

class secondscreen extends StatefulWidget{
  @override
  _MyHomePageState createState() => _MyHomePageState();

}


class _MyHomePageState extends State<secondscreen>{
  final List<taskname> _messages = <taskname>[];
  final TextEditingController _textController = new TextEditingController();

  void _handleSubmitted(String text) {
    _textController.clear();
    taskname message = new taskname(                         //new
      text: text,                                                  //new
    );                                                             //new
    setState(() {                                                  //new
      _messages.insert(0, message);                                //new
    });                                                            //new
  }

  @override //new
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(title: new Text("Task Manager")),
      body: new Column(                                        //modified
        children: <Widget>[                                         //new
          new Flexible(                                             //new
            child: new ListView.builder(                            //new
              padding: new EdgeInsets.all(8.0),                     //new
              reverse: true,                                        //new
              itemBuilder: (_, int index) => _messages[index],      //new
              itemCount: _messages.length,                          //new
            ),                                                      //new
          ),                                                        //new
          new Divider(height: 1.0),                                 //new
          new Container(                                            //new
            decoration: new BoxDecoration(
                color: Theme.of(context).cardColor),                  //new
            child: _buildTextComposer(),                       //modified
          ),                                                        //new
        ],                                                          //new
      ),                                                            //new
    );
  }

  Widget _buildTextComposer() {
    return new IconTheme( //new
      data: new IconThemeData(color: Theme
          .of(context)
          .accentColor), //new
      child: new Container( //modified
        margin: const EdgeInsets.symmetric(horizontal: 18.0),
        child: new Row(
          children: <Widget>[
            new Flexible(
              child: new TextField(
                controller: _textController,
                onSubmitted: _handleSubmitted,
                decoration: new InputDecoration.collapsed(
                    hintText: "Enter a new task"),

              ),
            ),
            new Container(
              margin: new EdgeInsets.symmetric(horizontal: 4.0),
              child: new IconButton(
                  icon: new Icon(Icons.send),
                  onPressed: () => _handleSubmitted(_textController.text)),
            ),
          ],
        ),
      ), //new
    );
  }
}

class taskname extends StatelessWidget {
  taskname({this.text});
  final String text;
  @override
  Widget build(BuildContext context) {
    return new Container(
      margin: const EdgeInsets.symmetric(vertical: 10.0),
      child: new Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          new Container(
            margin: const EdgeInsets.only(right: 10.0),
            child: new Text(text),


          ),

        ],
      ),
    );
  }
}
/*  final String title;


}



  Widget _buildTextComposer() {
    return new Container(
      margin: const EdgeInsets.symmetric(horizontal: 8.0),
      child: new TextField(
        controller: _textController,
        onSubmitted: _handleSubmitted,
        decoration: new InputDecoration.collapsed(
            hintText: "Send a message"),
      ),
    );
  }

  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(title: new Text("Friendlychat")),
      body: new Column(                                        //modified
        children: <Widget>[                                         //new
          new Flexible(                                             //new
            child: new ListView.builder(                            //new
              padding: new EdgeInsets.all(8.0),                     //new
              reverse: true,                                        //new
              itemBuilder: (_, int index) => _messages[index],      //new
              itemCount: _messages.length,                          //new
            ),                                                      //new
          ),                                                        //new
          new Divider(height: 1.0),                                 //new
          new Container(                                            //new
            decoration: new BoxDecoration(
                color: Theme.of(context).cardColor),                  //new
            child: _buildTextComposer(),                       //modified
          ),                                                        //new
        ],                                                          //new
      ),                                                            //new
    );
  }

  }

  void _addtask(){
    _task createState() => _task();
  }

}

class _task extends StatelessWidget {
  _task({this.text});
  final String text;
  @override
  Widget build(BuildContext context) {
    return new Container(
      margin: const EdgeInsets.symmetric(vertical: 10.0),
      child: new Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          new Container(
            margin: const EdgeInsets.only(right: 16.0),
            child: new CircleAvatar(child: new Text(_name[0])),
          ),
          new Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              new Text(_name, style: Theme.of(context).textTheme.subhead),
              new Container(
                margin: const EdgeInsets.only(top: 5.0),
                child: new Text(text),
              ),
            ],
          ),
        ],
      ),
    );
  }
}

void _handleSubmitted(String text) {
  _textController.clear();
}
class ChatScreenState extends State<ChatScreen> {
  final List<ChatMessage> _messages = <ChatMessage>[]; // new
  final TextEditingController _textController = new TextEditingController();
}
*/